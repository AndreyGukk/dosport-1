package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.entities.UserSportType;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.exceptions.DataNotSavedException;
import ru.dosport.repositories.UserSportTypeRepository;
import ru.dosport.services.api.UserSportTypeService;

import static ru.dosport.helpers.Messages.DATA_WAS_NOT_SAVED;
import static ru.dosport.helpers.Messages.USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE;

/**
 * Реализация сервиса видов спорта пользователя
 */
@Service
@RequiredArgsConstructor
public class UserSportTypeServiceImpl implements UserSportTypeService {

    // Необходимые сервисы и мапперы
    private final UserSportTypeRepository userSportTypeRepository;

    @Override
    public UserSportType findByUserIdAndSportTypeId(long userId, short sportTypeId) {
        return userSportTypeRepository.findByUserIdAndSportTypeId(userId, sportTypeId).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE, sportTypeId, userId)));
    }

    @Override
    public UserSportType save(long userId, short sportTypeId, short level) {
        return saveOrUpdate(userId, sportTypeId, level);
    }

    @Override
    public UserSportType update(long userId, short sportTypeId, short level) {
        return saveOrUpdate(userId, sportTypeId, level);
    }

    @Override
    public boolean deleteByUserId(long userId) {
        userSportTypeRepository.deleteById(userId);
        return userSportTypeRepository.existsById(userId);
    }

    /**
     * Сохранить или обновить сущность
     */
    private UserSportType saveOrUpdate(long userId, short sportTypeId, short level){
        if (userSportTypeRepository.findByUserIdAndSportTypeId(userId, sportTypeId).isPresent()){
            return userSportTypeRepository.update(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        } else {
            return userSportTypeRepository.save(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        }
    }

    //todo добавить методы получения списков и изменения
}
