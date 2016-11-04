package org.habv.mnemosyne.controller;

import com.github.javafaker.Faker;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.habv.mnemosyne.SpringDozerConfig;
import static org.habv.mnemosyne.controller.AdminEntryController.TEMPLATE;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.model.EntryForm;
import org.habv.mnemosyne.repository.EntryRepository;
import static org.habv.mnemosyne.util.StringUtil.toURL;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 26/10/2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminEntryController.class)
@ActiveProfiles("test")
@Import(SpringDozerConfig.class)
public class AdminEntryControllerTest {

    private final Faker faker = new Faker();
    private Entry entry;
    private EntryForm entryForm;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EntryRepository entryRepository;

    @Before
    public void setUp() {
        entry = new Entry();
        entry.setTitle(faker.lorem().sentence());
        entry.setPath(toURL(entry.getTitle()));
        entry.setContent(faker.lorem().paragraph());
        entry.setCategory(faker.lorem().word());
        entry.setTags(new HashSet<>(faker.lorem().words(3)));

        entryForm = new EntryForm();
        entryForm.setTitle(entry.getTitle());
        entryForm.setContent(entry.getContent());
        entryForm.setCategory(entry.getCategory());
        entryForm.setTags(entry.getTags());
    }

    private String setToString(Set<String> set) {
        return set.stream().collect(Collectors.joining(", "));
    }

    /**
     * Test of newEntry method, of class AdminEntryController.
     */
    @Test
    @WithMockUser
    public void testNewEntry_Get() throws Exception {
        System.out.println("AdminEntryController.newEntry Get");

        mvc.perform(get("/admin/entries/new")
                .accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE))
                .andExpect(model().attributeExists(EntryForm.ENTRY))
                .andExpect(model().attribute(EntryForm.ENTRY, is(notNullValue(EntryForm.class))))
                .andExpect(model().attribute(EntryForm.ENTRY, is(equalTo(new EntryForm()))))
                .andExpect(forwardedUrl(TEMPLATE));
    }

    /**
     * Test of newEntry method, of class AdminEntryController.
     */
    @Test
    @WithMockUser
    public void testNewEntry_Post() throws Exception {
        System.out.println("AdminEntryController.newEntry Post");

        given(entryRepository.insert(entry))
                .willReturn(entry);

        mvc.perform(post("/admin/entries/new")
                .param("title", entry.getTitle())
                .param("content", entry.getContent())
                .param("category", entry.getCategory())
                .param("tags", setToString(entry.getTags()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/blog/" + entry.getPath()));
    }

    /**
     * Test of newEntry method, of class AdminEntryController.
     */
    @Test
    @WithMockUser
    public void testNewEntry_Post_Error() throws Exception {
        System.out.println("AdminEntryController.newEntry Post (error)");

        mvc.perform(post("/admin/entries/new")
                .param("title", "")
                .param("content", "")
                .param("category", "")
                .param("tags", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrors(EntryForm.ENTRY, "title", "content", "category", "tags"))
                .andExpect(forwardedUrl(TEMPLATE));
    }

    /**
     * Test of editEntry method, of class AdminEntryController.
     */
    @Test
    @WithMockUser
    public void testEditEntry_Get() throws Exception {
        System.out.println("AdminEntryController.editEntry Get");

        given(entryRepository.findByPath(entry.getPath()))
                .willReturn(Optional.of(entry));

        mvc.perform(get("/admin/entries/edit/{path}", entry.getPath())
                .accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE))
                .andExpect(model().attributeExists(EntryForm.ENTRY))
                .andExpect(model().attribute(EntryForm.ENTRY, is(notNullValue(EntryForm.class))))
                .andExpect(model().attribute(EntryForm.ENTRY, is(equalTo(entryForm))))
                .andExpect(forwardedUrl(TEMPLATE));
    }

    /**
     * Test of editEntry method, of class AdminEntryController.
     */
    @Test
    @WithMockUser
    public void testEditEntry_Post() throws Exception {
        System.out.println("AdminEntryController.editEntry Post");

        given(entryRepository.findByPath(entry.getPath()))
                .willReturn(Optional.of(entry));
        given(entryRepository.save(entry))
                .willReturn(entry);

        mvc.perform(post("/admin/entries/edit/{path}", entry.getPath())
                .param("title", entry.getTitle())
                .param("content", entry.getContent())
                .param("category", entry.getCategory())
                .param("tags", setToString(entry.getTags()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/blog/" + entry.getPath()));
    }

    /**
     * Test of editEntry method, of class AdminEntryController.
     */
    @Test
    @WithMockUser
    public void testEditEntry_Post_Error() throws Exception {
        System.out.println("AdminEntryController.editEntry Post (error)");

        mvc.perform(post("/admin/entries/edit/{path}", entry.getPath())
                .param("title", "")
                .param("content", "")
                .param("category", "")
                .param("tags", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrors(EntryForm.ENTRY, "title", "content", "category", "tags"))
                .andExpect(forwardedUrl(TEMPLATE));
    }
}
