package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.entities.UserSport;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.exceptions.DataNotSavedException;
import ru.dosport.repositories.UserSportRepository;
import ru.dosport.services.api.UserSportService;

import static ru.dosport.helpers.Messages.DATA_WAS_NOT_SAVED;
import static ru.dosport.helpers.Messages.USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE;

/**
 * Реализация сервиса видов спорта пользователя
 */
@Service
@RequiredArgsConstructor
public class UserSportServiceImpl implements UserSportService {

    // Необходимые сервисы и мапперы
    private final UserSportRepository userSportRepository;

    @Override
    public UserSport findByUserIdAndSportTypeId(long userId, short sportTypeId) {
        return userSportRepository.findByUserIdAndSportTypeId(userId, sportTypeId).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE, sportTypeId, userId)));
    }

    @Override
    public UserSport save(long userId, short sportTypeId, short level) {
        return saveOrUpdate(userId, sportTypeId, level);
    }

    @Override
    public UserSport update(long userId, short sportTypeId, short level) {
        return saveOrUpdate(userId, sportTypeId, level);
    }

    @Override
    public boolean deleteByUserId(long userId) {
        userSportRepository.deleteById(userId);
        return userSportRepository.existsById(userId);
    }

    /**
     * Сохранить или обновить сущность
     */
    private UserSport saveOrUpdate(long userId, short sportTypeId, short level){
        if (userSportRepository.findByUserIdAndSportTypeId(userId, sportTypeId).isPresent()){
            return userSportRepository.update(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        } else {
            return userSportRepository.save(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        }
    }
}
