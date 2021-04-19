package ru.dosport.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.dosport.entities.Authority;
import ru.dosport.enums.AuthorityType;
import ru.dosport.entities.User;
import ru.dosport.enums.Gender;
import ru.dosport.exceptions.JwtAuthenticationException;
import ru.dosport.exceptions.OAuth2AuthenticationProcessingException;
import ru.dosport.repositories.AuthorityRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.security.JwtUser;
import ru.dosport.security.oauth2.user.OAuth2UserInfo;
import ru.dosport.security.oauth2.user.OAuth2UserInfoFactory;

import java.util.*;

import static ru.dosport.helpers.InformationMessages.EMAIL_NOT_RESPOND;
import static ru.dosport.helpers.InformationMessages.USER_ALREADY_REGISTERED;
import static ru.dosport.helpers.Roles.ROLE_USER;
/**
 * Сервис пользователей при работе через OAuth2
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private String registrationId;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User;
        registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase();
        if (registrationId.equals(AuthorityType.VK.toString())) {
            oAuth2User = loadVkUser(oAuth2UserRequest);
        } else if (registrationId.equals(AuthorityType.MAIL.toString())){
            oAuth2User = loadMailUser(oAuth2UserRequest);
        }else {
            oAuth2User = super.loadUser(oAuth2UserRequest);
        }
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            //При выбрасывании AuthenticationException сработает OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException(String.format(EMAIL_NOT_RESPOND,oAuth2UserRequest.getClientRegistration().getProviderDetails()));
        }

        Optional<User> userOptional = userRepository.findByUsername(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getAuthorityType().equals(AuthorityType.valueOf(registrationId))) {
                throw new OAuth2AuthenticationProcessingException(String.format(USER_ALREADY_REGISTERED,user.getAuthorityType()));
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserInfo);
        }

        return JwtUser.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setAuthorityType(AuthorityType.valueOf(registrationId));
        user.setSocialId(oAuth2UserInfo.getId());
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setUsername(oAuth2UserInfo.getEmail());
        user.setPhotoLink(oAuth2UserInfo.getImageUrl());
        user.setEnabled(true);
        user.setGender(Gender.NOT_SELECTED);
        Authority authority = authorityRepository.findByAuthority(ROLE_USER);
        user.getAuthorities().add(authority);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setPhotoLink(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

    private OAuth2User loadVkUser(OAuth2UserRequest oAuth2UserRequest) {

        String uri = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        uri = uri.replace("{id}", userNameAttributeName + "=" + oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));

        try {
            ResponseEntity<Object> entity =  new RestTemplate().exchange(uri, HttpMethod.GET, httpRequest(oAuth2UserRequest), Object.class);
            Map<String, Object> response = (Map) entity.getBody();
            ArrayList valueList = (ArrayList) response.get("response");
            Map<String, Object> userAttributes = (Map<String, Object>) valueList.get(0);
            userAttributes.put("email", oAuth2UserRequest.getAdditionalParameters().get("email"));
            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);

        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            throw new JwtAuthenticationException(ex.getMessage());
        }
    }

    private OAuth2User loadMailUser(OAuth2UserRequest oAuth2UserRequest) {

        String uri = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        uri = uri.replace("{id}", userNameAttributeName + "=" + oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));
        uri = uri+"?access_token="+oAuth2UserRequest.getAccessToken().getTokenValue();

        try {
            ResponseEntity<Object> entity =  new RestTemplate().exchange(uri, HttpMethod.GET, httpRequest(oAuth2UserRequest), Object.class);
            Map<String, Object> userAttributes = (Map) entity.getBody();

            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);

        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            throw new JwtAuthenticationException(ex.getMessage());
        }
    }

    private  HttpEntity<?> httpRequest(OAuth2UserRequest oAuth2UserRequest){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", oAuth2UserRequest.getAccessToken().getTokenType().getValue() + " " + oAuth2UserRequest.getAccessToken().getTokenValue());
        HttpEntity<?> httpRequest = new HttpEntity(headers);
        return httpRequest;
    }

}
