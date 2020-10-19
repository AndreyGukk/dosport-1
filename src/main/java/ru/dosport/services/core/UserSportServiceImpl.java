package ru.dosport.services.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dosport.entities.UserSport;
import ru.dosport.exceptions.EntityBadRequestException;
import ru.dosport.repositories.UserSportRepository;
import ru.dosport.services.api.UserSportService;

import static ru.dosport.entities.Messages.*;

import java.util.Optional;

@Log4j2
@Service
public class UserSportServiceImpl implements UserSportService {

    private UserSportRepository userSportRepository;

    @Autowired
    public void setUserSportRepository(UserSportRepository userSportRepository) {
        this.userSportRepository = userSportRepository;
    }

    @Override
    public UserSport findByUserIdAndSportTypeId(long userId, short sportTypeId) {
        Optional<UserSport> userSportOptional = userSportRepository.findByUserIdAndSportTypeId(userId, sportTypeId);
        if(userSportOptional.isPresent()){
            return userSportOptional.get();
        }else{
            log.debug(USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE + "(userID = " + userId + ", sportTypeId = " + sportTypeId + ")");
            return null;
        }
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
        boolean result = userSportRepository.existsById(userId);
        if(result) {
            userSportRepository.deleteById(userId);
        }
        return result;
    }

    private UserSport saveOrUpdate(long userId, short sportTypeId, short level){
        Optional<UserSport> userSportOptional = userSportRepository.findByUserIdAndSportTypeId(userId, sportTypeId);
        if(userSportOptional.isPresent()){
            return userSportRepository.update(userId, sportTypeId, level).get();
        }else{
            return userSportRepository.save(userId, sportTypeId, level).get();
        }
    }
}
