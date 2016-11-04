package org.habv.mnemosyne.controller;

import java.util.List;
import javax.validation.Valid;
import org.dozer.Mapper;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.model.EntryForm;
import org.habv.mnemosyne.repository.EntryRepository;
import static org.habv.mnemosyne.util.StringUtil.toURL;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 24/10/2016
 */
@Controller
@RequestMapping("/admin/entries")
public class AdminEntryController {

    public static final String TEMPLATE = "admin/entryForm";
    public static final String REDIRECT_BLOG = "redirect:/blog/";
    private final EntryRepository entryRepository;
    private final Mapper mapper;

    public AdminEntryController(
            EntryRepository entryRepository,
            Mapper mapper) {

        this.entryRepository = entryRepository;
        this.mapper = mapper;
    }

    @ModelAttribute("categories")
    public List<String> getCategories() {
        return entryRepository.findDistinctCategories();
    }

    @ModelAttribute("tags")
    public List<String> getTags() {
        return entryRepository.findDistinctTags();
    }

    @GetMapping("/new")
    public String newEntry(@ModelAttribute(EntryForm.ENTRY) EntryForm entry) {
        return TEMPLATE;
    }

    @PostMapping("/new")
    public String newEntry(
            @Valid @ModelAttribute(EntryForm.ENTRY) EntryForm entryForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return TEMPLATE;
        }

        Entry entry = mapper.map(entryForm, Entry.class);
        entry.setPath(toURL(entry.getTitle()));
        entry = entryRepository.insert(entry);
        return REDIRECT_BLOG + entry.getPath();
    }

    @GetMapping("/edit/{path:[a-z0-9-]+}")
    public ModelAndView editEntry(@PathVariable String path) {
        return entryRepository
                .findByPath(path)
                .map(entry -> new ModelAndView(TEMPLATE)
                        .addObject(
                                EntryForm.ENTRY,
                                mapper.map(entry, EntryForm.class)))
                .orElse(new NotFoundView());
    }

    @PostMapping("/edit/{path:[a-z0-9-]+}")
    public ModelAndView editEntry(
            @PathVariable String path,
            @Valid @ModelAttribute(EntryForm.ENTRY) EntryForm entryForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TEMPLATE);
        }

        return entryRepository
                .findByPath(path)
                .map(entry -> {
                    entry.getTags().clear();
                    mapper.map(entryForm, entry);
                    entry.setPath(toURL(entry.getTitle()));
                    return entryRepository.save(entry);
                })
                .map(entry -> new ModelAndView(REDIRECT_BLOG + entry.getPath()))
                .orElse(new NotFoundView());
    }

}
