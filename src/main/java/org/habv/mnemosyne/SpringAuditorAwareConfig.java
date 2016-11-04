package org.habv.mnemosyne;

import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 25/10/2016
 */
@Configuration
@EnableMongoAuditing
public class SpringAuditorAwareConfig implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        return Optional
                .ofNullable(
                        SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> auth.getName())
                .orElse("Anonymous User");
    }

}
