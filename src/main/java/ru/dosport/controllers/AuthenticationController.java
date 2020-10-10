package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dosport.dto.AuthenticationRequest;
import ru.dosport.entities.JwtUser;
import ru.dosport.security.JwtTokenProvider;
import ru.dosport.services.api.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер аутентификации.
 */
@Api("Контроллер аутентификации")
@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    // Список необходимых зависимостей
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @ApiOperation(value = "Осуществляет авторизацию пользователя и выдачу токена авторизации")
    @PostMapping("login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequest requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            JwtUser user = userService.getJwtUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Пользователь с именем: " + username + " не найден");
            }

            String token = jwtTokenProvider.createToken(username,
                    user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("roles", user.getAuthorities());
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверное имя пользователя или пароль");
        }
    }
}
