package ru.dosport.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурация ссылок перенаправления после авторизации
 * */
@Configuration
public class RedirectUriConfiguration {

    private final OAuth2 oauth2 = new OAuth2();

    public static final class OAuth2 {
        @Value("${app.oauth2.authorizedRedirectUris[0]}")
        private String webUri;
        @Value("${app.oauth2.authorizedRedirectUris[1]}")
        private String androidUri;
        @Value("${app.oauth2.authorizedRedirectUris[2]}")
        private String iosUri;

        private List<String> authorizedRedirectUris = new ArrayList<>();
        {
            authorizedRedirectUris.add(webUri);
            authorizedRedirectUris.add(androidUri);
            authorizedRedirectUris.add(iosUri);
        }

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }
}
