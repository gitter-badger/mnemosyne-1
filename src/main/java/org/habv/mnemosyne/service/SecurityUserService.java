package org.habv.mnemosyne.service;

import java.util.Locale;
import java.util.Optional;
import org.habv.mnemosyne.model.SecurityUser;
import org.habv.mnemosyne.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.toStringArray;

/**
 * @author Herman Barrantes
 * @since 01/10/2016
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public SecurityUserService(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        String userNotFound = messageSource.getMessage("security.user_not_found", null, locale);
        Optional
                .ofNullable(username)
                .filter(name -> !name.trim().isEmpty())
                .orElseThrow(() -> new UsernameNotFoundException(userNotFound));
        return userRepository
                .findByEmailAndEnabledTrue(username.trim())
                .map(user -> new SecurityUser(
                        user.getEmail(),
                        user.getPassword(),
                        createAuthorityList(toStringArray(user.getRoles())),
                        user.isEnabled()))
                .orElseThrow(() -> new UsernameNotFoundException(userNotFound));
    }

}
