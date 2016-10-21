package org.habv.mnemosyne.repository;

import java.util.Optional;
import org.habv.mnemosyne.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 04/08/2016
 */
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAndEnabledIsTrue(String email);
}
