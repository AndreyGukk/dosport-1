package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dosport.entities.JwtUser;
import ru.dosport.services.api.UserService;

import static ru.dosport.entities.Messages.USER_NOT_FOUND_BY_USERNAME;

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
        JwtUser user = userService.getJwtByUsername(username);
        if (user == null) {
            log.debug(String.format(USER_NOT_FOUND_BY_USERNAME, username));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username));
        }
        return user;
    }
}
