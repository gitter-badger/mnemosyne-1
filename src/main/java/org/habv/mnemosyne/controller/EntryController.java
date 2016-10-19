package org.habv.mnemosyne.controller;

import org.habv.mnemosyne.repository.EntryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 18/10/2016
 */
@Controller
public class EntryController {

    public static final String TEMPLATE = "entry";
    private final EntryRepository entryRepository;

    public EntryController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @GetMapping("/blog/{path:[a-z0-9-]+}")
    public ModelAndView entry(@PathVariable String path) {
        return entryRepository
                .findByPath(path)
                .map(entry -> new ModelAndView(TEMPLATE, TEMPLATE, entry))
                .orElse(new NotFoundView());
    }

}
