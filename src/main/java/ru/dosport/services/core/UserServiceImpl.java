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
    public UserDto getUserDtoById(Long userId) {
        return userMapper.userToUserDto(findById(userId));
    }

    @Override
    public User getUserByUsername(String username) {
        return findByUsername(username);
    }

    @Override
    public JwtUser getJwtUserByUsername(String username) {
        return userMapper.userToJwtUser(findByUsername(username));
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        return userMapper.userToUserDto(findByUsername(username));
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.userToUserDto(userRepository.findAll());
    }

    @Override
    public boolean deleteById(Long userId) {
        userRepository.deleteById(userId);
        return userRepository.existsById(userId);
    }

    @Override
    public UserDto save(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirm())) {
            log.info(PASSWORD_MISMATCH);
            throw new EntityBadRequestException(PASSWORD_MISMATCH);
        }
        String username = userRequest.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            log.info(String.format(USER_HAS_ALREADY_CREATED, username));
            throw new EntityBadRequestException(String.format(USER_HAS_ALREADY_CREATED, username));
        }

        User newUser = userMapper.userRequestToUser(userRequest);
        newUser.setEnabled(true);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setCreationDate(LocalDate.now());
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
            log.info(PASSWORD_MISMATCH);
            throw new EntityBadRequestException(PASSWORD_MISMATCH);
        }

        User user = findByUsername(authentication.getName());
        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new EntityBadRequestException(OLD_PASSWORD_INVALID);
        } else {
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }
    }

    /**
     * Найти пользователя по id
     */
    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
    }

    /**
     * Найти пользователя по username
     */
    private User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username)));
    }
}
