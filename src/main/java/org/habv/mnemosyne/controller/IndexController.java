package org.habv.mnemosyne.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("mensaje", "Hola Mundo");
        return "index";
    }
}
