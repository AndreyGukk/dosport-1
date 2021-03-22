package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.entities.User;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_FOUND_BY_ID;

/**
 * Реализация сервиса Видов спорта.
 */
@Service
@RequiredArgsConstructor
public class SportTypeServiceImpl implements SportTypeService {

    // Необходимые сервисы, мапперы и репозитории
    private final SportTypeMapper sportTypeMapper;
    private final SportTypeRepository repository;
    private final UserService userService;

    @Override
    public SportTypeDto getSportTypeDtoById(Short id) {
        return sportTypeMapper.mapEntityToDto(findSportById(id));
    }

    @Override
    public List<SportTypeDto> getAllSportTypeDto() {
        return sportTypeMapper.mapEntityToDto(repository.findAll());
    }

    @Override
    public SportType getSportTypeByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, title)));
    }

    @Transactional
    @Override
    public SportTypeDto addSportByAuthentication(String sportTitle) {
        Optional<SportType> sport = repository.findByTitle(sportTitle);
        return sport.isPresent() ?
                sportTypeMapper.mapEntityToDto(sport.get()) :
                sportTypeMapper.mapEntityToDto(repository.save(new SportType(sportTitle)));
    }

    @Override
    public Boolean deleteById(Short id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public SportTypeDto update(Short id, String tittle) {
        SportType sportType = findSportById(id);
        sportType.setTitle(tittle);
        return sportTypeMapper.mapEntityToDto(repository.save(sportType));
    }

    /*
     * Методы, относящиеся к предпочитаемым видам спорта пользователя
     */

    @Override
    public List<SportTypeDto> getAllSportDtoByAuthentication(Authentication authentication) {
        return sportTypeMapper.mapEntityToDto(findUserByAuthentication(authentication).getSports());
    }

    @Override
    public List<SportTypeDto> updateSportsByAuthentication(List<SportTypeDto> dtoList, Authentication authentication) {
        List<SportType> sportTypeList = repository.findAllById(sportTypeMapper.mapDtoToShort(dtoList));
        User user = findUserByAuthentication(authentication);
        user.getSports().addAll(sportTypeList);
        return sportTypeMapper.mapEntityToDto(userService.save(user).getSports());
    }

    @Transactional
    @Override
    public boolean addSportByAuthentication(Short sportTypeId, Authentication authentication) {
        User user = findUserByAuthentication(authentication);
        SportType sportType = findSportById(sportTypeId);
        user.getSports().add(sportType);
        return userService.save(user).getSports().contains(sportType);
    }

    @Transactional
    @Override
    public boolean deleteSportByAuthentication(Short sportTypeId, Authentication authentication) {
        User user = findUserByAuthentication(authentication);
        SportType sportType = findSportById(sportTypeId);
        user.getSports().remove(sportType);
        return !userService.save(user).getSports().contains(sportType);
    }

    /**
     * Найти вид спорта по идентификатору
     */
    private SportType findSportById(Short id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    /**
     * Найти пользователя по данным авторизации
     */
    private User findUserByAuthentication(Authentication authentication) {
        return userService.getByAuthentication(authentication);
    }
}
