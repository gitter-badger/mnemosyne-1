package org.habv.mnemosyne.controller;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.service.EntryService;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 13/10/2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@EnableSpringDataWebSupport
@ActiveProfiles("test")
public class HomeControllerTest {

    private static final int ENTRIES_SIZE = 10;
    private static final String ENTRIES = Entry.COLLECTION_NAME;
    private final Faker faker = new Faker();
    private String author;
    private String category;
    private String tag;
    private Page<Entry> pageWithData;
    private Page<Entry> pageWithoutData;

    @Autowired
    private MockMvc mvc;

    @Value("${config.first-page:0}")
    private Integer firstPage;
    @Value("${config.page-size:20}")
    private Integer pageSize;

    @MockBean
    private EntryService entryService;

    @Before
    public void setUp() {
        pageWithData = getPageWithData();
        pageWithoutData = getPageWithoutData();
    }

    /**
     * Get page with data.
     *
     * @return page with data
     */
    private Pageable getPageableWithData() {
        return new PageRequest(firstPage, pageSize);
    }

    /**
     * Get page without data.
     *
     * @return page without data
     */
    private Pageable getPageableWithoutData() {
        return new PageRequest(1, pageSize);
    }

    /**
     * Get a page with fake data.
     *
     * @return page fake data
     */
    private Page<Entry> getPageWithData() {

        List<Entry> entries = new ArrayList<>(ENTRIES_SIZE);

        author = faker.internet().emailAddress();
        category = faker.lorem().word();
        tag = faker.lorem().word();

        for (int i = 0; i < ENTRIES_SIZE; i++) {
            Entry entry = new Entry();
            entry.setTitle(faker.lorem().sentence());
            entry.setContent(faker.lorem().paragraph());
            entry.setDate(faker.date().past(7, TimeUnit.DAYS));
            entry.setAuthor(author);
            entry.setCategory(category);
            entry.setTags(Arrays.asList(tag));

            entries.add(entry);
        }
        return new PageImpl<>(entries, getPageableWithData(), ENTRIES_SIZE);
    }

    /**
     * Get an empty page.
     *
     * @return an empty page
     */
    private Page<Entry> getPageWithoutData() {
        return new PageImpl<>(Collections.emptyList(), getPageableWithoutData(), 0L);
    }

    /**
     * Generic validations of HomeController.
     *
     * @param actions result of perform action
     * @throws Exception
     */
    private void checkWithData(ResultActions actions) throws Exception {
        actions
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(ENTRIES))
                .andExpect(model().attributeExists(ENTRIES))
                .andExpect(model().attribute(ENTRIES, is(notNullValue())))
                .andExpect(model().attribute(ENTRIES, hasProperty("content", is(not(empty())))))
                .andExpect(model().attribute(ENTRIES, hasProperty("content", hasSize(ENTRIES_SIZE))))
                .andExpect(forwardedUrl(ENTRIES));
    }

    /**
     * Generic validations of HomeController.
     *
     * @param actions result of perform action
     * @throws Exception
     */
    private void checkWithoutData(ResultActions actions) throws Exception {
        actions
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(ENTRIES))
                .andExpect(model().attributeDoesNotExist(ENTRIES))
                .andExpect(forwardedUrl(ENTRIES));
    }

    /**
     * Test of index method with unauthenticated user, of class HomeController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testIndex_Unauthenticated() throws Exception {
        System.out.println("HomeController.index (unauthenticated)");

        mvc.perform(get("/").accept(MediaType.TEXT_HTML))
                .andExpect(unauthenticated());
    }

    /**
     * Test of index method, of class HomeController.
     *
     * @throws java.lang.Exception
     */
    @Test
    @WithMockUser
    public void testIndex() throws Exception {
        System.out.println("HomeController.index");

        given(entryService.findByOrderByDateDesc(getPageableWithData()))
                .willReturn(pageWithData);

        ResultActions actions = mvc.perform(get("/").accept(MediaType.TEXT_HTML));
        checkWithData(actions);
    }

    /**
     * Test of index method, of class HomeController.
     *
     * @throws java.lang.Exception
     */
    @Test
    @WithMockUser
    public void testIndex_WithoutData() throws Exception {
        System.out.println("HomeController.index (without data)");

        given(entryService.findByOrderByDateDesc(getPageableWithoutData()))
                .willReturn(pageWithoutData);

        ResultActions actions = mvc.perform(get("/").accept(MediaType.TEXT_HTML));
        checkWithoutData(actions);
    }

    /**
     * Test of page method, of class HomeController.
     */
    @Test
    @WithMockUser
    public void testPage() throws Exception {
        System.out.println("HomeController.page");

        given(entryService.findByOrderByDateDesc(getPageableWithData()))
                .willReturn(pageWithData);

        ResultActions actions = mvc.perform(get("/page/{page}", firstPage).accept(MediaType.TEXT_HTML));
        checkWithData(actions);
    }

    /**
     * Test of tag method, of class HomeController.
     */
    @Test
    @WithMockUser
    public void testTag() throws Exception {
        System.out.println("HomeController.tag");

        given(entryService.findByTagsOrderByDateDesc(tag, getPageableWithData()))
                .willReturn(pageWithData);

        ResultActions actions = mvc.perform(get("/tag/{tag}", tag).accept(MediaType.TEXT_HTML));
        checkWithData(actions);
    }

    /**
     * Test of tagPage method, of class HomeController.
     */
    @Test
    @WithMockUser
    public void testTagPage() throws Exception {
        System.out.println("HomeController.tagPage");

        given(entryService.findByTagsOrderByDateDesc(tag, getPageableWithData()))
                .willReturn(pageWithData);

        ResultActions actions = mvc.perform(get("/tag/{tag}/page/{page}", tag, firstPage).accept(MediaType.TEXT_HTML));
        checkWithData(actions);
    }

    /**
     * Test of category method, of class HomeController.
     */
    @Test
    @WithMockUser
    public void testCategory() throws Exception {
        System.out.println("HomeController.category");

        given(entryService.findByCategoryOrderByDateDesc(category, getPageableWithData()))
                .willReturn(pageWithData);

        ResultActions actions = mvc.perform(get("/category/{category}", category).accept(MediaType.TEXT_HTML));
        checkWithData(actions);
    }

    /**
     * Test of categoryPage method, of class HomeController.
     */
    @Test
    @WithMockUser
    public void testCategoryPage() throws Exception {
        System.out.println("HomeController.categoryPage");

        given(entryService.findByCategoryOrderByDateDesc(category, getPageableWithData()))
                .willReturn(pageWithData);

        ResultActions actions = mvc.perform(get("/category/{category}/page/{page}", category, firstPage).accept(MediaType.TEXT_HTML));
        checkWithData(actions);
    }
}
