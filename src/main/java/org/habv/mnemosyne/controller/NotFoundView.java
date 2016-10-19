package org.habv.mnemosyne.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 18/10/2016
 */
public class NotFoundView extends ModelAndView {

    public static final String TEMPLATE = "error/404";

    public NotFoundView() {
        super(TEMPLATE);
        super.setStatus(HttpStatus.NOT_FOUND);
    }

}
