package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.UserSportGroundDto;

import java.util.List;

public interface UserSportGroundService {
    List<UserSportGroundDto> getAllDtoByAuth(Authentication authentication);
    UserSportGroundDto addDtoByAuth (Authentication authentication, SportGroundDto sportTypeDto);
    boolean deleteBySportGroundId(Long id, Authentication authentication);

}
