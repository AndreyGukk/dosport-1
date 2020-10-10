package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dosport.entities.JwtUser;
import ru.dosport.services.api.UserService;

/**
 * Сервис, отвечающий за авторизацию пользователей.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    // Необходимые сервисы
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser user = userService.getJwtUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с именем: " + username + " не найден");
        }
        log.info("Пользователь с именем: {} успешно загружен", username);
        return user;
    }
}
