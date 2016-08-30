package org.habv.mnemosyne;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

/**
 * @author Herman Barrantes
 * @since 11/08/2016
 */
@Configuration
@EnableMongoHttpSession(maxInactiveIntervalInSeconds = 300)
public class SpringSessionConfig {

    @Bean
    public JdkMongoSessionConverter jdkMongoSessionConverter() {
        return new JdkMongoSessionConverter();
    }
}
