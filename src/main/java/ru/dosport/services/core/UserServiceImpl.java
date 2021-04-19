package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.Authority;
import ru.dosport.enums.AuthorityType;
import ru.dosport.enums.Gender;
import ru.dosport.entities.User;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.AuthorityRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.security.JwtUser;
import ru.dosport.services.api.MailService;
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
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final MailService mailService;

    @Value("${app.user.activation_url}")
    private String activationUrl;

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

    @Override
    public UserDto save(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirm())) {
            throw new DataBadRequestException(PASSWORDS_MISMATCH);
        }
        String username = userRequest.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DataBadRequestException(String.format(USER_ALREADY_EXIST, username));
        }

        User newUser = userMapper.mapDtoToEntity(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setGender(Gender.NOT_SELECTED);
        Authority authority = authorityRepository.findByAuthority(ROLE_USER);
        newUser.getAuthorities().add(authority);
        newUser.setAuthorityType(AuthorityType.REGULAR);
        userRepository.save(newUser);
        sendEmail(newUser);
        return userMapper.mapEntityToDto(newUser);
    }

    @Override
    public String activate(String activationCode) {
        User activatedUser = findByActivationCode(activationCode);
        activatedUser.setActivationCode(null);
        activatedUser.setEnabled(true);
        userRepository.save(activatedUser);
        return VALID_ACTIVATION_CODE;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto update(UserDto userDto, Authentication authentication) {
        User user = userMapper.update(findById(getUserId(authentication)), userDto);
        return userMapper.mapEntityToDto(userRepository.save(user));
    }

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

    @Override
    public boolean deleteByAuthentication(Authentication authentication) {
        Long id = getUserId(authentication);
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public List<UserDto> getSubscribersByAuthentication(Authentication authentication) {
        User user = findById(getUserId(authentication));
        return userMapper.mapEntityToDto(user.getSubscribers());
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
     * Найти пользователя по activationCode
     */
    private User findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode).orElseThrow(
                () -> new DataNotFoundException(INVALID_ACTIVATION_CODE));
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
     * Отправить код активации пользователю
     */
    private void sendEmail(User user) {
        String username = user.getUsername();
        mailService.sendEmail(
                    username,
                    ACTIVATION_CODE,
                    String.format(
                            USER_ACTIVATION_CODE,
                            username,
                            activationUrl,
                            user.getActivationCode()
                    )
            );
    }
}
