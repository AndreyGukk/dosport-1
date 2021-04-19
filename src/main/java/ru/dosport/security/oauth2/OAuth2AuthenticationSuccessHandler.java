package ru.dosport.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.dosport.configs.RedirectUriConfiguration;
import ru.dosport.dto.AuthenticationDto;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.security.JwtTokenProvider;
import ru.dosport.security.JwtUser;
import ru.dosport.util.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.dosport.helpers.InformationMessages.USER_WAS_FOUND;
import static ru.dosport.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
/**
 * Ответ при успешной авторизации.
 */
@Log4j2
@Component
@AllArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private JwtTokenProvider jwtTokenProvider;

    private RedirectUriConfiguration redirectUriConfiguration;

    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            log.debug("Ответ уже передан. Невозможно перенаправить по " + targetUrl);
            return;
        }
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(jwtUser.getUsername(),jwtUser.getId(),
                jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        log.debug(String.format(USER_WAS_FOUND, jwtUser.getUsername()));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new AuthenticationDto(token));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        clearAuthenticationAttributes(request, response);
        //  getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new DataBadRequestException("Продолжение аутентификации невозможно. Незарегистрированный адрес.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(jwtUser.getUsername(),jwtUser.getId(),
                jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return redirectUriConfiguration.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Проверять только хост и порт. Путь клиент определяет самостоятельно
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
