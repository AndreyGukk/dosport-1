package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.dto.UserSportGroundDto;
import ru.dosport.entities.SportGround;
import ru.dosport.entities.UserSportGround;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.SportGroundMapper;
import ru.dosport.mappers.UserSportGroundMapper;
import ru.dosport.repositories.SportGroundRepository;
import ru.dosport.repositories.UserSportGroundRepository;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.UserService;

import java.util.ArrayList;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Сервис Спортивных площадок.
 */
@Service
@RequiredArgsConstructor
public class SportGroundServiceImp implements SportGroundService {

    // Репозитории
    private final SportGroundRepository groundRepository;
    private final UserSportGroundRepository userSportGroundRepository;

    // Мапперы
    private final SportGroundMapper groundMapper;
    private final UserSportGroundMapper userSportGroundMapper;
    private final UserService userService;

    @Override
    public SportGroundDto getDtoById(Long id) {
        return groundMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<SportGroundDto> getAllDto() {
        return groundMapper.mapEntityToDto(groundRepository.findAll());
    }

    @Override
    public List<SportGroundDto> getAllDto(String city) {
        return city == null ? getAllDto() : groundMapper.mapEntityToDto(groundRepository.findAllByCity(city));
    }

    @Override
    public List<SportGroundDto> getAllDtoById(List<Long> idList) {
        return groundMapper.mapEntityToDto(groundRepository.findAllById(idList));
    }

    @Override
    public SportGround getById(Long id) {
        return findById(id);
    }

    @Transactional
    @Override
    public SportGroundDto create(SportGroundRequest request) {
        SportGround ground = groundMapper.mapRequestToEntity(request);

        return groundMapper.mapEntityToDto(groundRepository.save(ground));
    }

    @Override
    public SportGroundDto update(Long id, SportGroundDto sportGroundDto, Authentication authentication) {
        checkAdminAccess(authentication);

        SportGround sportGround = findById(id);

        if (!sportGround.getId().equals(sportGroundDto.getSportGroundId())) {
            throw new DataBadRequestException("Площадка указана не коректнно");
        }

        return groundMapper.mapEntityToDto(groundRepository.save(groundMapper.update(sportGround, sportGroundDto)));
    }

    @Transactional
    @Override
    public boolean delete(Long id, Authentication authentication) {
        checkAdminAccess(authentication);

        groundRepository.deleteById(id);
        return groundRepository.existsById(id);
    }

    /**
     * Найти по идентификатору
     */
    private SportGround findById(Long id) {
        return groundRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    private void checkAdminAccess(Authentication authentication) {
        if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
            throw new AccessDeniedException("Пользователь не является админом");
        }
    }

    @Override
    public List<SportGroundDto> getAllDtoByAuth(Authentication authentication) {
        List<SportGroundDto> result = new ArrayList<>();
        userSportGroundMapper.mapEntityToDto(userSportGroundRepository.findAllByUserId(userService.getIdByAuthentication(authentication))).forEach(s -> result.add(getDtoById(s.getSportGroundId())));
        return result;
    }

    @Override
    public UserSportGroundDto addDtoByAuth(Authentication authentication, SportGroundDto sportGroundDto) {
        UserSportGround userSportGround = UserSportGround.builder()
                .userId(userService.getIdByAuthentication(authentication))
                .sportgroundsId(sportGroundDto.getSportGroundId())
                .build();
        return userSportGroundMapper.mapEntityToDto(userSportGroundRepository.save(userSportGround));

    }

    @Override
    public boolean deleteBySportGroundId(Long id, Authentication authentication) {
        userSportGroundRepository.deleteByUserIdAndSportGroundId(userService.getIdByAuthentication(authentication), id);
        return !userSportGroundRepository.findByUserIdAndSportGroundId(userService.getIdByAuthentication(authentication), id).isPresent();
    }
}
