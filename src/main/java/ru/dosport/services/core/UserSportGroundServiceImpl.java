package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.UserSportGroundDto;
import ru.dosport.entities.UserSportGround;
import ru.dosport.mappers.UserSportGroundMapper;
import ru.dosport.repositories.UserSportGroundRepository;
import ru.dosport.services.api.UserService;
import ru.dosport.services.api.UserSportGroundService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserSportGroundServiceImpl implements UserSportGroundService {

    private final UserSportGroundRepository userSportGroundRepository;
    private final UserService userService;
    private final UserSportGroundMapper mapper;


    @Override
    public List<UserSportGroundDto> getAllDtoByAuth(Authentication authentication) {
        return mapper.mapEntityToDto(userSportGroundRepository.findAllByUserId(userService.getIdByAuthentication(authentication)));
    }

    @Override
    public UserSportGroundDto addDtoByAuth(Authentication authentication, SportGroundDto sportGroundDto) {
        UserSportGround userSportGround = UserSportGround.builder()
                .userId(userService.getIdByAuthentication(authentication))
                .sportgroundsId(sportGroundDto.getSportGroundId())
                .build();
        return mapper.mapEntityToDto(userSportGroundRepository.save(userSportGround));

    }

    @Override
    public boolean deleteBySportGroundId(Long id, Authentication authentication) {
        userSportGroundRepository.deleteByUserIdAndSportGroundId(userService.getIdByAuthentication(authentication), id);
        return !userSportGroundRepository.findByUserIdAndSportGroundId(userService.getIdByAuthentication(authentication), id).isPresent();
    }


}
