package org.habv.mnemosyne.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Herman Barrantes
 * @since 12/08/2016
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(CsrfToken csrf, Model model) {
        model.addAttribute("csrf", csrf);
        return "login";
    }
}
