package ru.dosport.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.dosport.entities.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Jwt обертка сущности Пользователь.
 */
@Setter
@NoArgsConstructor
public class JwtUser implements OAuth2User, UserDetails {

    private Long id;

    private String username;

    private String password;

    private List<JwtRole> authorities;

    private Map<String, Object> attributes;

    public JwtUser(Long id, String email, String password, List<JwtRole> authorities) {
        this.id = id;
        this.username = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static JwtUser create(User user) {
        List<JwtRole> authorities = Collections.
                singletonList(new JwtRole("ROLE_USER"));
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public static JwtUser create(User user, Map<String, Object> attributes) {
        JwtUser jwtUser = JwtUser.create(user);
        jwtUser.setAttributes(attributes);
        return jwtUser;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }
}
