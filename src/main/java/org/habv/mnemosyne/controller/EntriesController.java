package org.habv.mnemosyne.controller;

import org.habv.mnemosyne.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Controller
public class EntriesController {

    @Autowired
    private EntryRepository entryRepository;
    @Value("${spring.data.rest.default-page-size:20}")
    private Integer size;

    @GetMapping({"/", "/page"})
    public String index(Model model, Pageable pageable) {
        model.addAttribute("entries", entryRepository.findAll(pageable));
        return "entries";
    }

    @GetMapping("/page/{page}")
    public String page(@PathVariable("page") Integer page, Model model) {
        Pageable pageable = new PageRequest(page <= 0 ? 0 : page - 1, size);
        model.addAttribute("entries", entryRepository.findAll(pageable));
        return "entries";
    }
}
