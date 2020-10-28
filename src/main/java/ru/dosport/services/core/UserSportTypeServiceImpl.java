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
        return userSportTypeMapper.mapEntityToDto(userSportTypeRepository.findAllByUserId(userService.getIdByAuthentication(authentication)));
    }

    @Override
    public List<UserSportTypeDto> updateByUserId(List<UserSportTypeDto> dtoList, Authentication authentication) {
        userSportTypeMapper.mapDtoToEntity(dtoList).forEach(s -> saveOrUpdate(userService.getIdByAuthentication(authentication), s.getSportTypeId(), s.getLevel()));
        return getAllDtoByUserId(authentication);
    }

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
    public boolean deleteBySportTypeId(Authentication authentication, Short sportTypeId) {
        userSportTypeRepository.deleteBySportTypeId(userService.getIdByAuthentication(authentication), sportTypeId);
        return userSportTypeRepository.findByUserIdAndSportTypeId(userService.getIdByAuthentication(authentication), sportTypeId) == null;
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
