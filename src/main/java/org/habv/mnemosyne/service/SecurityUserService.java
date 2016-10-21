package org.habv.mnemosyne.service;

import java.util.Optional;
import java.util.stream.Collectors;
import org.habv.mnemosyne.model.SecurityUser;
import org.habv.mnemosyne.repository.UserRepository;
import org.springframework.context.MessageSource;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 01/10/2016
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public SecurityUserService(
            UserRepository userRepository,
            MessageSource messageSource) {

        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional
                .ofNullable(username)
                .filter(name -> !name.trim().isEmpty())
                .orElseThrow(this::getException);
        return userRepository
                .findByEmailAndEnabledIsTrue(username.trim())
                .map(user -> new SecurityUser(
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()),
                        user.isEnabled()))
                .orElseThrow(this::getException);
    }

    private UsernameNotFoundException getException() {
        String userNotFoundMessage = messageSource.getMessage("security.user_not_found", null, getLocale());
        return new UsernameNotFoundException(userNotFoundMessage);
    }

}
