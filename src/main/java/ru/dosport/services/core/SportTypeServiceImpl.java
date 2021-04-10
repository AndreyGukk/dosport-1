package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.entities.SportType;
import ru.dosport.entities.User;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.Set;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_FOUND_BY_TITLE;

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
    public Set<String> getAllSportType() {
        return sportTypeMapper.mapEntityToString(repository.findAll());
    }

    @Override
    public SportType getSportTypeByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_TITLE, title)));
    }

    @Override
    public Set<SportType> getAllSportTypesByTitle(Set<String> titles) {
        return repository.findAllByTitleIn(titles);
    }

    @Transactional
    @Override
    public Boolean addSportType(String sportTitle) {
        if (!repository.existsByTitle(sportTitle)) {
            repository.save(new SportType(sportTitle));
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean deleteByTitle(String sportTitle) {
        repository.deleteByTitle(sportTitle);
        return repository.existsByTitle(sportTitle);
    }

    /*
     * Методы, относящиеся к предпочитаемым видам спорта пользователя
     */

    @Override
    public Set<String> getAllSportTypeByAuthentication(Authentication authentication) {
        return sportTypeMapper.mapEntityToString(findUserByAuthentication(authentication).getSports());
    }

    @Transactional
    @Override
    public boolean addSportTypeToFavorite(String sportTypeTitle, Authentication authentication) {
        SportType sportType = getSportTypeByTitle(sportTypeTitle);
        User user = findUserByAuthentication(authentication);
        if (user.getSports().add(sportType)) {
            return userService.save(user).getSports().contains(sportType);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteSportByAuthentication(String sportTypeTitle, Authentication authentication) {
        SportType sportType = getSportTypeByTitle(sportTypeTitle);
        User user = findUserByAuthentication(authentication);
        if (user.getSports().remove(sportType)) {
            return !userService.save(user).getSports().contains(sportType);
        }
        return true;
    }

    /**
     * Найти пользователя по данным авторизации
     */
    private User findUserByAuthentication(Authentication authentication) {
        return userService.getByAuthentication(authentication);
    }
}
