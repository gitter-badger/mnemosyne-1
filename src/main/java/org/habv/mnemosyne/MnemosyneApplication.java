package org.habv.mnemosyne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@SpringBootApplication
@EnableRedisHttpSession
public class MnemosyneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MnemosyneApplication.class, args);
    }
}
