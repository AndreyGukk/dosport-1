package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.security.JwtTokenProvider;
import ru.dosport.security.JwtUser;
import ru.dosport.services.api.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static ru.dosport.helpers.InformationMessages.*;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/auth", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "/api/v1/auth", tags = {"Контроллер Авторизации и регистрации пользователя"})
public class AuthenticationController {

    // Список необходимых зависимостей
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "Осуществляет выдачу токена авторизации пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    public ResponseEntity<AuthenticationDto> login(
            @ApiParam("Запрос авторизации") @Valid @RequestBody AuthenticationRequest requestDto
    ) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            JwtUser user = userService.getJwtByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username));
            }
            log.debug(String.format(USER_WAS_FOUND, username));

            String token = jwtTokenProvider.createToken(username, user.getId(),
                    user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

            return ResponseEntity.ok(new AuthenticationDto(token));
        } catch (AuthenticationException e) {
            log.debug(e);
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "Создает новый профиль пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> createUser(
            @ApiParam("Запрос для регистрации нового Пользователя") @Valid @RequestBody UserRequest userRequest
    ) {
        return ResponseEntity.ok(userService.save(userRequest));
    }

    @PostMapping(value = "/activate/{activationCode}")
    @ApiOperation(value = "Активирует пользователя с помощью кода активации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<?> activateUser(
            @ApiParam("Код активации") @PathVariable String activationCode
    ) {
        return ResponseEntity.ok(userService.activateUser(activationCode));
    }
}
