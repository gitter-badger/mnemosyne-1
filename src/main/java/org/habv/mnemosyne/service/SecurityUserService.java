package org.habv.mnemosyne.service;

import java.util.Optional;
import org.habv.mnemosyne.model.SecurityUser;
import org.habv.mnemosyne.model.User;
import org.habv.mnemosyne.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Herman Barrantes
 * @since 01/10/2016
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        Optional<User> oUser = userRepository.findByEmail(username);
        return oUser
                .filter(user -> user.isEnabled())
                .map(user -> new SecurityUser(
                        user.getEmail(),
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList(user.getRoles().toArray(new String[0])),
                        user.isEnabled()))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

}
