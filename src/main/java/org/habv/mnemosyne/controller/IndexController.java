package org.habv.mnemosyne.controller;

import org.habv.mnemosyne.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Controller
public class IndexController {
    
    @Autowired
    private EntryRepository entryRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, Pageable pageable) {
//        Pageable pag = new PageRequest(page, pageSize);
        model.addAttribute("entries", entryRepository.findAll(pageable));
        return "index";
    }
}