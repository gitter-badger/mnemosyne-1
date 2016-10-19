package org.habv.mnemosyne;

import org.habv.mnemosyne.controller.NotFoundView;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 12/08/2016
 */
@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry
                .addViewController("/login")
                .setViewName("login");
        registry
                .addViewController("/" + NotFoundView.TEMPLATE)
                .setStatusCode(HttpStatus.NOT_FOUND)
                .setViewName(NotFoundView.TEMPLATE);
    }

}
