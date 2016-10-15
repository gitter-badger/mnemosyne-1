package org.habv.mnemosyne.controller;

import lombok.extern.slf4j.Slf4j;
import org.habv.mnemosyne.exception.MvcNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 14/10/2016
 */
@Slf4j
@ControllerAdvice
public class MvcErrorController {

    private static final String EXCEPTION = "exception";

    @ExceptionHandler(MvcNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound(MvcNotFoundException ex) {
        log.error("MvcErrorController.notFound: {}", ex.getLocalizedMessage(), ex);
        return new ModelAndView("error/404", EXCEPTION, ex.getMessage());
    }
}
