package ru.dosport.security.oauth2.user;

import ru.dosport.enums.AuthorityType;
import ru.dosport.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

import static ru.dosport.helpers.InformationMessages.SOCIAL_NOT_RESPOND;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthorityType.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.VK.toString())) {
            return new VkOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.APPLE.toString())) {
            return new AppleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.BITBUCKET.toString())) {
            return new BitbucketOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.YANDEX.toString())) {
            return new YandexOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthorityType.MAIL.toString())) {
            return new MailOAuth2UserInfo(attributes);
        }else {
            throw new OAuth2AuthenticationProcessingException(String.format(SOCIAL_NOT_RESPOND, registrationId));
        }
    }
}
