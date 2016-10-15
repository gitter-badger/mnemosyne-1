package org.habv.mnemosyne.model;

import java.util.Collection;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 01/10/2016
 */
@Value
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 7433718822494784923L;

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
}
