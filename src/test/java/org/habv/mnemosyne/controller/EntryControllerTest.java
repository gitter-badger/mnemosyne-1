package org.habv.mnemosyne.controller;

import com.github.javafaker.Faker;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static org.habv.mnemosyne.controller.EntryController.TEMPLATE;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.repository.EntryRepository;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 18/10/2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EntryController.class)
@ActiveProfiles("test")
public class EntryControllerTest {

    private final Faker faker = new Faker();
    private Entry entry;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EntryRepository entryRepository;

    @Before
    public void setUp() {
        entry = new Entry();
        entry.setId(faker.crypto().md5());
        entry.setTitle(faker.lorem().sentence());
        entry.setPath("path-to-test");
        entry.setContent(faker.lorem().paragraph());
        entry.setDate(faker.date().past(7, TimeUnit.DAYS));
        entry.setAuthor(faker.internet().emailAddress());
        entry.setCategory(faker.lorem().word());
        entry.setTags(faker.lorem().words(3));
    }

    /**
     * Test of entry method, of class EntryController.
     */
    @Test
    @WithMockUser
    public void testEntry() throws Exception {
        System.out.println("EntryController.entry");

        given(entryRepository.findByPath(entry.getPath()))
                .willReturn(Optional.of(entry));

        mvc.perform(get("/blog/{path}", entry.getPath()).accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE))
                .andExpect(model().attributeExists(TEMPLATE))
                .andExpect(model().attribute(TEMPLATE, is(notNullValue())))
                .andExpect(model().attribute(TEMPLATE, is(equalTo(entry))))
                .andExpect(forwardedUrl(TEMPLATE));
    }

    /**
     * Test of entry method with not found, of class EntryController.
     */
    @Test
    @WithMockUser
    public void testEntry_NotFound() throws Exception {
        System.out.println("EntryController.entry (not found)");

        String path = "not-found";
        given(entryRepository.findByPath(path))
                .willReturn(Optional.empty());

        mvc.perform(get("/blog/{path}", path).accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isNotFound())
                .andExpect(view().name(NotFoundView.TEMPLATE))
                .andExpect(model().attributeDoesNotExist(TEMPLATE))
                .andExpect(forwardedUrl(NotFoundView.TEMPLATE));
    }

}
