package org.habv.mnemosyne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@SpringBootApplication
@EnableMongoHttpSession
public class MnemosyneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MnemosyneApplication.class, args);
    }
}
