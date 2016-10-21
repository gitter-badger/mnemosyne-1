package org.habv.mnemosyne.service;

import com.github.javafaker.Faker;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.habv.mnemosyne.model.User;
import org.habv.mnemosyne.repository.UserRepository;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 20/10/2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SecurityUserServiceTest {

    private final Faker faker = new Faker();

    private User user;

    @Autowired
    private SecurityUserService securityUserService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        //Valid User
        user = new User();
        user.setId(faker.crypto().md5());
        user.setName(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.crypto().sha256());
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        user.setEnabled(true);
    }

    /**
     * Test of loadUserByUsername method, of class SecurityUserService.
     */
    @Test
    public void testLoadUserByUsername() {
        System.out.println("SecurityUserService.loadUserByUsername");

        given(userRepository.findByEmailAndEnabledIsTrue(user.getEmail()))
                .willReturn(Optional.of(user));

        UserDetails result = securityUserService.loadUserByUsername(user.getEmail());
        assertThat(result, is(notNullValue(UserDetails.class)));
        assertThat(result.isEnabled(), is(true));
        assertThat(result.getUsername(), is(equalTo(user.getEmail())));
        assertThat(result.getPassword(), is(equalTo(user.getPassword())));
        List<String> authorities = result
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        assertThat(authorities, contains("USER", "ADMIN"));
    }

    /**
     * Test of loadUserByUsername method with not found user, of class
     * SecurityUserService.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_NotFoundUser() {
        System.out.println("SecurityUserService.loadUserByUsername (not found user)");

        given(userRepository.findByEmailAndEnabledIsTrue(user.getEmail()))
                .willReturn(Optional.empty());

        securityUserService.loadUserByUsername(user.getEmail());
        fail("It will throw a UsernameNotFoundException.");
    }

    /**
     * Test of loadUserByUsername method with null user, of class
     * SecurityUserService.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_NullUser() {
        System.out.println("SecurityUserService.loadUserByUsername (null user)");

        securityUserService.loadUserByUsername(null);
        fail("It will throw a UsernameNotFoundException.");
    }

    /**
     * Test of loadUserByUsername method with empty user, of class
     * SecurityUserService.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_EmptyUser() {
        System.out.println("SecurityUserService.loadUserByUsername (empty user)");

        securityUserService.loadUserByUsername("");
        fail("It will throw a UsernameNotFoundException.");
    }

}
