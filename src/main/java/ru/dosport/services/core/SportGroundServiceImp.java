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
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.SportGroundMapper;
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
    public List<SportGroundDto> getAllDtoByIdList(List<Long> idList) {
        return groundMapper.mapEntityToDto(groundRepository.findAllById(idList));
    }

    @Override
    public List<SportGroundDto> getAllDtoByCityAndSportTypeId(String city, Short sportTypeId) {
        if (city == null) {
            return sportTypeId == null ?
                    getAllDto() :
                    groundMapper.mapEntityToDto(groundRepository.findAllBySportTypes(sportTypeId));
        } else {
            return sportTypeId == null ?
                    groundMapper.mapEntityToDto(groundRepository.findAllByCity(city)) :
                    groundMapper.mapEntityToDto(groundRepository.findAllByCityAndSportTypes(city, sportTypeId));
        }
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
    public SportGroundDto update(Long id, SportGroundRequest request, Authentication authentication) {
        checkAdminAccess(authentication);

        var sportGround = findById(id);

        return groundMapper.mapEntityToDto(groundRepository.save(groundMapper.update(sportGround, request)));
    }

    @Transactional
    @Override
    public boolean delete(Long id, Authentication authentication) {
        checkAdminAccess(authentication);

        groundRepository.deleteById(id);
        return groundRepository.existsById(id);
    }

    @Override
    public boolean exists(Long sportGroundId) {
        return groundRepository.existsById(sportGroundId);
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
    public List<SportGroundDto> getFavoriteSportGroundsByAuth(Authentication authentication) {
        List<SportGroundDto> result = new ArrayList<>();
        groundMapper.mapUserSportGroundEntityToDto(userSportGroundRepository
                .findAllByUserId(userService.getIdByAuthentication(authentication)))
                .forEach(s -> result.add(getDtoById(s.getSportGroundId())));
        return result;
    }

    @Override
    public UserSportGroundDto saveFavoriteSportGroundByAuthAndId(Authentication authentication, Long id) {
        UserSportGround userSportGround = UserSportGround.builder()
                .userId(userService.getIdByAuthentication(authentication))
                .sportGroundId(id)
                .build();
        return groundMapper.mapUserSportGroundEntityToDto(userSportGroundRepository.save(userSportGround));

    }

    @Override
    public boolean deleteFavoriteSportGroundByAuthAndId(Authentication authentication, Long id) {
        userSportGroundRepository.deleteByUserIdAndSportGroundId(userService.getIdByAuthentication(authentication), id);
        return !userSportGroundRepository.findByUserIdAndSportGroundId(userService.getIdByAuthentication(authentication), id).isPresent();
    }
}
