package org.habv.mnemosyne.controller;

import java.util.Optional;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.model.EntryForm;
import org.habv.mnemosyne.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 04/08/2016
 */
@Controller
public class HomeController {

    public static final String TEMPLATE = "entries";
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

    private ModelAndView loadPage(Page<Entry> entries) {
        return Optional
                .ofNullable(entries)
                .filter(Slice::hasContent)
                .map(page -> new ModelAndView(TEMPLATE)
                        .addObject(EntryForm.ENTRIES, page))
                .orElse(new NotFoundView());
    }

    @GetMapping({"/", "/page"})
    public ModelAndView index(Pageable pageable) {
        return loadPage(entryRepository.findByOrderByDateDesc(pageable));
    }

    @GetMapping("/page/{page:[0-9]+}")
    public ModelAndView page(@PathVariable Integer page) {
        return loadPage(entryRepository.findByOrderByDateDesc(getPage(page)));
    }

    @GetMapping("/tag/{tag}")
    public ModelAndView tag(@PathVariable String tag, Pageable pageable) {
        return loadPage(entryRepository.findByTagsOrderByDateDesc(tag, pageable));
    }

    @GetMapping("/tag/{tag}/page/{page:[0-9]+}")
    public ModelAndView tagPage(@PathVariable String tag, @PathVariable Integer page) {
        return loadPage(entryRepository.findByTagsOrderByDateDesc(tag, getPage(page)));
    }

    @GetMapping("/category/{category}")
    public ModelAndView category(@PathVariable String category, Pageable pageable) {
        return loadPage(entryRepository.findByCategoryOrderByDateDesc(category, pageable));
    }

    @GetMapping("/category/{category}/page/{page:[0-9]+}")
    public ModelAndView categoryPage(@PathVariable String category, @PathVariable Integer page) {
        return loadPage(entryRepository.findByCategoryOrderByDateDesc(category, getPage(page)));
    }

}
