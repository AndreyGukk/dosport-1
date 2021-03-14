package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.entities.UserSportType;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.exceptions.DataNotSavedException;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.repositories.UserSportTypeRepository;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.dosport.helpers.Messages.*;

/**
 * Реализация сервиса Видов спорта.
 */
@Service
@RequiredArgsConstructor
public class SportTypeServiceImpl implements SportTypeService {

    // Необходимые мапперы и репозитории
    private final SportTypeMapper sportTypeMapper;
    private final SportTypeRepository repository;
    private final UserSportTypeRepository userSportTypeRepository;
    private final UserService userService;

    @Override
    public SportTypeDto getSportTypeDtoById(Short id) {
        return sportTypeMapper.mapEntityToDto(findById(id));
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
    public SportTypeDto save(String sportTitle) {
        Optional<SportType> sport = repository.findByTitle(sportTitle);
        return sport.isPresent() ?
                sportTypeMapper.mapEntityToDto(sport.get()) : sportTypeMapper.mapEntityToDto(repository.save(new SportType(sportTitle)));
    }

    @Override
    public Boolean deleteById(Short id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public SportTypeDto update(Short id, String tittle) {
        SportType sportType = findById(id);
        sportType.setTitle(tittle);
        return sportTypeMapper.mapEntityToDto(repository.save(sportType));
    }

    /*
     * Методы, относящиеся к предпочитаемым видам спорта пользователя
     */

    @Override
    public List<SportTypeDto> getAllDtoByUserId(Long id) {
        return sportTypeMapper.mapUserEntityToDto(userSportTypeRepository.findAllByUserId(id));
    }

    @Override
    public List<SportTypeDto> getAllDtoByUserAuthentication(Authentication authentication) {
        return sportTypeMapper.mapUserEntityToDto(
                userSportTypeRepository.findAllByUserId(userService.getIdByAuthentication(authentication)));
    }

    // TODO
    @Override
    public List<SportTypeDto> update(List<SportTypeDto> dtoList, Authentication authentication) {
        return getAllDtoByUserAuthentication(authentication);
    }

    private UserSportType getByUserIdAndSportTypeId(long userId, short sportTypeId) {
        return userSportTypeRepository.findByUserIdAndSportTypeId(userId, sportTypeId).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE, sportTypeId, userId)));
    }

    @Override
    public SportTypeDto save(long userId, short sportTypeId, short level) {
        return sportTypeMapper.mapEntityToDto(saveOrUpdate(userId, sportTypeId, level));
    }

    @Override
    public boolean delete(Authentication authentication, short sportTypeId) {
        userSportTypeRepository.deleteBySportTypeId(userService.getIdByAuthentication(authentication), sportTypeId);
        return getByUserIdAndSportTypeId(userService.getIdByAuthentication(authentication), sportTypeId) == null;
    }

    /**
     * Сохранить или обновить сущность
     */
    private UserSportType saveOrUpdate(long userId, short sportTypeId, short level) {
        if (userSportTypeRepository.findByUserIdAndSportTypeId(userId, sportTypeId).isPresent()) {
            return userSportTypeRepository.update(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        } else {
            return userSportTypeRepository.save(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        }
    }

    /**
     * Найти по идентификатору
     */
    private SportType findById(Short id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }
}
