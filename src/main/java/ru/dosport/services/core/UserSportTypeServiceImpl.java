package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.UserSportType;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.exceptions.DataNotSavedException;
import ru.dosport.mappers.UserSportTypeMapper;
import ru.dosport.repositories.UserSportTypeRepository;
import ru.dosport.services.api.SportTypeService;
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
    private final SportTypeService sportTypeService;

    @Override
    public List<UserSportTypeDto> getAllDtoByUserId(Long id) {
        return userSportTypeMapper.mapEntityToDto(userSportTypeRepository.findAllByUserId(id));
    }

    @Override
    public List<UserSportTypeDto> getAllDtoByUserAuthentication(Authentication authentication) {
        return userSportTypeMapper.mapEntityToDto(userSportTypeRepository.findAllByUserId(userService.getIdByAuthentication(authentication)));
    }

    @Override
    public List<SportTypeDto> getEmptyDtoByUser(Authentication authentication) {
        List<String> userList = new ArrayList<>();
        getAllDtoByUserAuthentication(authentication).forEach(userSportTypeDto -> userList.add(userSportTypeDto.getSportType())); //список ненулевых навыков пользователя
        return sportTypeService.getAllSportTypeDto().stream().filter(stDto->!userList.contains(stDto.getTitle())).collect(Collectors.toList());
    }

    @Override
    public List<UserSportTypeDto> update(List<UserSportTypeDto> dtoList, Authentication authentication) {
        userSportTypeMapper.mapDtoToEntity(dtoList).forEach(s -> saveOrUpdate(userService.getIdByAuthentication(authentication), s.getSportTypeId(), s.getLevel()));
        return getAllDtoByUserAuthentication(authentication);
    }

    @Override
    public UserSportType getByUserIdAndSportTypeId(long userId, short sportTypeId) {
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

}
