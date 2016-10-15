package org.habv.mnemosyne.repository;

import com.github.javafaker.Faker;
import java.util.Optional;
import org.habv.mnemosyne.model.User;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 11/10/2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private final Faker faker = new Faker();

    private User user1;
    private User user2;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        //Clean data
        userRepository.deleteAll();
        //Valid User
        user1 = new User();
        user1.setName(faker.name().fullName());
        user1.setEmail(faker.internet().emailAddress());
        user1.setEnabled(true);
        //Invalid User
        user2 = new User();
        user2.setName(faker.name().fullName());
        user2.setEmail(faker.internet().emailAddress());
        user2.setEnabled(false);
        //Save
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
    }

    /**
     * Test of findByEmailAndEnabledTrue method, of class UserRepository.
     */
    @Test
    public void testFindByEmailAndEnabledTrue() {
        System.out.println("UserRepository.findByEmailAndEnabledTrue");
        //Valid user
        Optional<User> result = userRepository.findByEmailAndEnabledTrue(user1.getEmail());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(equalTo(user1)));
    }

    /**
     * Test of findByEmailAndEnabledTrue method, of class UserRepository.
     */
    @Test
    public void testFindByEmailAndEnabledTrue_InvalidUser() {
        System.out.println("UserRepository.findByEmailAndEnabledTrue (invalid user)");
        //Invalid user
        Optional<User> result = userRepository.findByEmailAndEnabledTrue(user2.getEmail());
        assertThat(result.isPresent(), is(false));
    }

    /**
     * Test of findByEmailAndEnabledTrue method, of class UserRepository.
     */
    @Test
    public void testFindByEmailAndEnabledTrue_FalseUser() {
        System.out.println("UserRepository.findByEmailAndEnabledTrue (false user)");
        //False user
        Optional<User> result = userRepository.findByEmailAndEnabledTrue(faker.internet().emailAddress());
        assertThat(result.isPresent(), is(false));
    }

}
