package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.Authority;
import ru.dosport.entities.JwtUser;
import ru.dosport.entities.User;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.AuthorityRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.services.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.dosport.entities.Roles.ROLE_USER;
import static ru.dosport.entities.Roles.hasAuthenticationRoleAdmin;

/**
 * Сервис пользователей
 */
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
    public UserDto getDtoById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(userMapper::userToUserDto).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    @Override
    public JwtUser getJwtUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(userMapper::userToJwtUser).orElse(null);
    }

    @Override
    public UserDto getByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(userMapper::userToUserDto).orElse(null);
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
        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()
                || !userRequest.getPassword().equals(userRequest.getPasswordConfirm())
                || userRequest.getUsername() == null || userRequest.getUsername().isEmpty()
                || userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return null;
        }
        User newUser = new User(
                userRequest.getUsername(),
                passwordEncoder.encode(userRequest.getPassword()),
                true,
                LocalDate.now());
        Authority authority = authorityRepository.findByAuthority(ROLE_USER);
        newUser.getAuthorities().add(authority);
        return userMapper.userToUserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto update(UserDto userDto, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = userMapper.updateUser(optionalUser.get(), userDto);
            return userMapper.userToUserDto(userRepository.save(user));
        } else {
            return null;
        }
    }

    @Override
    public UserDto update(UserDto userDto, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = userMapper.updateUser(optionalUser.get(), userDto);
            return userMapper.userToUserDto(userRepository.save(user));
        } else {
            return null;
        }
    }

    @Override
    public User update(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
            User user = userMapper.updateUser(optionalUser.get(), userDto);
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean updatePassword(PasswordRequest passwordRequest,
                                  Authentication authentication) {
        if (!authentication.getName().equals(passwordRequest.getUsername())) {
            if (!hasAuthenticationRoleAdmin(authentication)) {
                return false;
            }
        }

        Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return false;
        }

        String oldPassword = passwordRequest.getOldPassword();
        String newPassword = passwordRequest.getNewPassword();

        if (oldPassword != null && newPassword != null && !newPassword.isEmpty() &&
                newPassword.equals(passwordRequest.getNewPasswordConfirm())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
