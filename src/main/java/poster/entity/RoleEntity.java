package poster.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEntity implements GrantedAuthority {

    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

}
