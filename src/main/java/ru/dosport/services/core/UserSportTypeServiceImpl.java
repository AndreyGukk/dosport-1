package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.UserSportType;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.exceptions.DataNotSavedException;
import ru.dosport.mappers.UserSportTypeMapper;
import ru.dosport.repositories.UserSportTypeRepository;
import ru.dosport.services.api.UserService;
import ru.dosport.services.api.UserSportTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final UserSportTypeMapper userSportTypeMapper;
    private final UserService userService;

    @Override
    public List<UserSportTypeDto> getAllDtoByUserId(Long id) {
        return userSportTypeMapper.mapEntityToDto(userSportTypeRepository.findAllByUserId(id));
    }

    @Override
    public List<UserSportTypeDto> getAllDtoByUserId(Authentication authentication) {
            return userSportTypeMapper.mapEntityToDto(userSportTypeRepository.findAllByUserId(userService.getByUsername(authentication.getName()).getId()));
    }

    @Override
    public List<UserSportTypeDto> updateByUserId(List<UserSportTypeDto> dtoList, Authentication authentication) {
        for (UserSportType s : userSportTypeMapper.mapDtoToEntity(dtoList)) {
            findByUserIdAndSportTypeId(userService.getByUsername(authentication.getName()).getId(), s.getSportTypeId()).setLevel(s.getLevel());
        }
        //todo переписать красиво
        return getAllDtoByUserId(authentication);
    }


    //далее писала не я, не знаю, зачем оно
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
    private UserSportType saveOrUpdate(long userId, short sportTypeId, short level) {
        if (userSportTypeRepository.findByUserIdAndSportTypeId(userId, sportTypeId).isPresent()) {
            return userSportTypeRepository.update(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        } else {
            return userSportTypeRepository.save(userId, sportTypeId, level).orElseThrow(
                    () -> new DataNotSavedException(DATA_WAS_NOT_SAVED));
        }
    }

}
