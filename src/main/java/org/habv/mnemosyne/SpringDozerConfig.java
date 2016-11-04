package org.habv.mnemosyne;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 25/10/2016
 */
@Configuration
public class SpringDozerConfig {

    @Bean
    public Mapper dozerBean() {
        return DozerBeanMapperSingletonWrapper.getInstance();
    }
}
