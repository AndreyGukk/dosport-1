package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.*;
import ru.dosport.entities.Authority;
import ru.dosport.entities.User;
import ru.dosport.enums.Gender;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.AuthorityRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.security.JwtUser;
import ru.dosport.services.api.UserService;

import java.util.List;
import java.util.UUID;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Roles.ROLE_USER;

/**
 * Сервис пользователей
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    // Необходимые сервисы, мапперы и репозитории
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDto getDtoById(Long id) {
        return userMapper.mapEntityToDto(findById(id));
    }

    @Override
    public UserDto getDtoByAuthentication(Authentication authentication) {
        return userMapper.mapEntityToDto(findById(getUserId(authentication)));
    }

    @Override
    public User getById(Long id) {
        return findById(id);
    }

    @Override
    public User getByAuthentication(Authentication authentication) {
        return findById(getUserId(authentication));
    }

    @Override
    public JwtUser getJwtByUsername(String username) {
        return userMapper.mapEntityToJwt(findByUsername(username));
    }

    @Override
    public Long getIdByAuthentication(Authentication authentication) {
        return getUserId(authentication);
    }

    @Transactional
    @Override
    public UserDto save(UserPasswordRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirm())) {
            throw new DataBadRequestException(PASSWORDS_MISMATCH);
        }
        User newUser = prepareNewUser(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userMapper.mapEntityToDto(userRepository.save(newUser));
    }

    @Transactional
    @Override
    public String save(UserEmailRequest userRequest) {
        User newUser = prepareNewUser(userRequest);
        newUser.setEmail(userRequest.getEmail());
        newUser.setUuid(UUID.randomUUID().toString());
        return userRepository.save(newUser).getUuid();
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto, Authentication authentication) {
        User user = userMapper.update(findById(getUserId(authentication)), userDto);
        return userMapper.mapEntityToDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public boolean updatePassword(PasswordRequest passwordRequest,
                                  Authentication authentication) {
        if (!passwordRequest.getNewPassword().equals(passwordRequest.getNewPasswordConfirm())) {
            throw new DataBadRequestException(PASSWORDS_MISMATCH);
        }

        User user = findByUsername(authentication.getName());
        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new DataBadRequestException(OLD_PASSWORD_INVALID);
        } else {
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    @Override
    public boolean deleteByAuthentication(Authentication authentication) {
        Long id = getUserId(authentication);
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Transactional
    @Override
    public JwtUser activateUser(String activationCode) {
        User user = userRepository.findByUuid(activationCode).orElseThrow(
                () -> new DataNotFoundException(BAD_ACTIVATION_CODE));
        user.setUuid(null);
        user.setEnabled(true);
        return userMapper.mapEntityToJwt(userRepository.save(user));
    }

    @Override
    public List<UserDto> getSubscribersByAuthentication(Authentication authentication) {
        List<User> users = userRepository.findSubscribersByUserId(getUserId(authentication));
        return userMapper.mapEntityToDto(users);
    }

    /*
     * Методы относящиеся к подпискам и подписчикам пользователя
     */

    @Override
    public List<UserDto> getSubscriptionsByAuthentication(Authentication authentication) {
        User user = findById(getUserId(authentication));
        return userMapper.mapEntityToDto(user.getSubscriptions());
    }

    @Transactional
    @Override
    public boolean addSubscriptionByAuthentication(Long subscriptionId, Authentication authentication) {
        User user = findById(getUserId(authentication));
        if (user.getId().equals(subscriptionId)) {
            throw new DataBadRequestException(CANNOT_SUBSCRIBE_TO_MYSELF);
        }
        User subscription = findById(subscriptionId);
        user.getSubscriptions().add(subscription);
        return userRepository.save(user).getSubscriptions().contains(subscription);
    }

    @Transactional
    @Override
    public boolean deleteSubscriptionByAuthentication(Long subscriptionId, Authentication authentication) {
        User user = findById(getUserId(authentication));
        User subscription = findById(subscriptionId);
        user.getSubscriptions().remove(subscription);
        return !userRepository.save(user).getSubscriptions().contains(subscription);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.mapEntityToJwt(findByUsername(username));
    }

    /**
     * Найти пользователя по id
     */
    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(USER_NOT_FOUND_BY_ID, id)));
    }

    /**
     * Найти пользователя по username
     */
    private User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username)));
    }

    /**
     * Получить id пользователя по данным аутентификации
     */
    private Long getUserId(Authentication authentication) {
        if (authentication == null) {
            throw new DataNotFoundException(ACCESS_DENIED);
        }
        return ((JwtUser) authentication.getPrincipal()).getId();
    }

    /**
     * Подготовить данные нового пользователя для сохранения
     */
    private User prepareNewUser(UserRequest request) {
        String username = request.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DataBadRequestException(String.format(USER_ALREADY_EXIST, username));
        }
        User newUser = userMapper.mapDtoToEntity(request);
        newUser.setGender(Gender.NOT_SELECTED);
        Authority authority = authorityRepository.findByAuthority(ROLE_USER);
        newUser.getAuthorities().add(authority);
        return newUser;
    }
}
