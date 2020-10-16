package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.Authority;
import ru.dosport.entities.JwtUser;
import ru.dosport.entities.User;
import ru.dosport.exceptions.EntityBadRequestException;
import ru.dosport.exceptions.EntityNotFoundException;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.AuthorityRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.services.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.dosport.entities.Messages.*;
import static ru.dosport.entities.Roles.ROLE_USER;

/**
 * Сервис пользователей
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Необходимые сервисы
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // Необходимые репозитории
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDto getDtoById(Long id) {
        return userMapper.userToUserDto(findById(id));
    }

    @Override
    public User getByUsername(String username) {
        return findByUsername(username);
    }

    @Override
    public JwtUser getJwtByUsername(String username) {
        return userMapper.userToJwtUser(findByUsername(username));
    }

    @Override
    public UserDto getDtoByUsername(String username) {
        return userMapper.userToUserDto(findByUsername(username));
    }

    @Override
    public List<UserDto> getAllDto() {
        return userMapper.userToUserDto(userRepository.findAll());
    }

    @Override
    public UserDto save(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirm())) {
            log.debug(PASSWORDS_MISMATCH);
            throw new EntityBadRequestException(PASSWORDS_MISMATCH);
        }
        String username = userRequest.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            log.debug(String.format(USER_HAS_ALREADY_CREATED, username));
            throw new EntityBadRequestException(String.format(USER_HAS_ALREADY_CREATED, username));
        }

        User newUser = userMapper.userRequestToUser(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Authority authority = authorityRepository.findByAuthority(ROLE_USER);
        newUser.getAuthorities().add(authority);
        return userMapper.userToUserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto update(UserDto userDto, String username) {
        User user = userMapper.updateUser(findByUsername(username), userDto);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto update(UserDto userDto, Long id) {
        User user = userMapper.updateUser(findById(id), userDto);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public User update(UserDto userDto) {
        User user = userMapper.updateUser(findByUsername(userDto.getUsername()), userDto);
        return userRepository.save(user);
    }

    @Override
    public boolean updatePassword(PasswordRequest passwordRequest,
                                  Authentication authentication) {
        if (!passwordRequest.getNewPassword().equals(passwordRequest.getNewPasswordConfirm())) {
            log.debug(PASSWORDS_MISMATCH);
            throw new EntityBadRequestException(PASSWORDS_MISMATCH);
        }

        User user = findByUsername(authentication.getName());
        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            log.debug(OLD_PASSWORD_INVALID);
            throw new EntityBadRequestException(OLD_PASSWORD_INVALID);
        } else {
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        userRepository.deleteById(id);
        return userRepository.existsById(id);
    }

    /**
     * Найти пользователя по id
     */
    private User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            log.debug(String.format(USER_NOT_FOUND_BY_ID, id));
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, id));
        }
    }

    /**
     * Найти пользователя по username
     */
    private User findByUsername(String username) {
        Optional<User> optionalUser =  userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            log.debug(String.format(USER_NOT_FOUND_BY_USERNAME, username));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username));
        }
    }
}
