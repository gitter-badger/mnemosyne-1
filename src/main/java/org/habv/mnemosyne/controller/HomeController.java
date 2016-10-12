package org.habv.mnemosyne.controller;

import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.repository.EntryRepository;
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
public class HomeController {

    private static final String ENTRIES = Entry.COLLECTION_NAME;
    private final Integer firstPage;
    private final Integer pageSize;
    private final EntryRepository entryRepository;

    public HomeController(
            @Value("${config.first-page:0}") Integer firstPage,
            @Value("${config.page-size:20}") Integer pageSize,
            EntryRepository entryRepository) {

        this.firstPage = firstPage;
        this.pageSize = pageSize;
        this.entryRepository = entryRepository;
    }

    private Pageable getPage(Integer page) {
        return new PageRequest(page <= firstPage ? firstPage : page - 1, pageSize);
    }

    @GetMapping({"/", "/page"})
    public String index(Model model, Pageable pageable) {
        model.addAttribute(ENTRIES, entryRepository.findByOrderByDateDesc(pageable));
        return ENTRIES;
    }

    @GetMapping("/page/{page:[0-9]+}")
    public String page(@PathVariable Integer page, Model model) {
        model.addAttribute(ENTRIES, entryRepository.findByOrderByDateDesc(getPage(page)));
        return ENTRIES;
    }

    @GetMapping("/tag/{tag}")
    public String tag(@PathVariable String tag, Model model, Pageable pageable) {
        model.addAttribute(ENTRIES, entryRepository.findByTagsOrderByDateDesc(tag, pageable));
        return ENTRIES;
    }

    @GetMapping("/tag/{tag}/page/{page:[0-9]+}")
    public String tagPage(@PathVariable String tag, @PathVariable Integer page, Model model) {
        model.addAttribute(ENTRIES, entryRepository.findByTagsOrderByDateDesc(tag, getPage(page)));
        return ENTRIES;
    }

    @GetMapping("/category/{category}")
    public String category(@PathVariable String category, Model model, Pageable pageable) {
        model.addAttribute(ENTRIES, entryRepository.findByCategoryOrderByDateDesc(category, pageable));
        return ENTRIES;
    }

    @GetMapping("/category/{category}/page/{page:[0-9]+}")
    public String categoryPage(@PathVariable String category, @PathVariable Integer page, Model model) {
        model.addAttribute(ENTRIES, entryRepository.findByCategoryOrderByDateDesc(category, getPage(page)));
        return ENTRIES;
    }
}
