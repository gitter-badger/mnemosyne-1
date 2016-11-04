package org.habv.mnemosyne.pebble;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 21/10/2016
 */
public abstract class AbstractSecurity {

    /**
     * Allows direct access to the current Authentication object obtained from
     * the SecurityContext.
     *
     * @return Authentication object obtained from the SecurityContext
     */
    protected Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * Allows direct access to the principal object representing the current
     * user.
     *
     * @return object representing the current user
     */
    protected Object getPrincipal() {
        return getAuthentication()
                .map(auth -> auth.getPrincipal())
                .get();
    }

    /**
     * Returns true if the user is not anonymous.
     *
     * @return true if the user is not anonymous
     */
    protected boolean isAuthenticated() {
        return getAuthentication()
                .filter(Authentication::isAuthenticated)
                .filter(auth -> !(auth instanceof AnonymousAuthenticationToken))
                .isPresent();
    }

    /**
     * Returns true if the current principal is an anonymous user.
     *
     * @return true if the current principal is an anonymous user
     */
    protected boolean isAnonymous() {
        return !isAuthenticated();
    }

    /**
     * Returns true if the current principal has any of the supplied roles.
     *
     * @param roles list of roles
     * @return true if the current principal has any of the supplied roles
     */
    protected boolean hasAnyRole(Collection<String> roles) {
        return getAuthentication()
                .map(auth -> auth
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(roles::contains))
                .orElse(false);
    }

    /**
     * Returns true if the current principal has the specified role.
     *
     * @param role role name
     * @return true if the current principal has the specified role
     */
    protected boolean hasRole(String role) {
        return hasAnyRole(Collections.singleton(role));
    }

}
