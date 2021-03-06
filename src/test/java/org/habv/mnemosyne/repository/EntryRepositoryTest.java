package org.habv.mnemosyne.repository;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.habv.mnemosyne.model.Entry;
import static org.habv.mnemosyne.util.StringUtil.toURL;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.util.StringUtils.toStringArray;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 11/10/2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EntryRepositoryTest {

    private static final int ENTRIES_SIZE = 50;
    private static final int CATEGORIES_SIZE = 5;
    private static final int TAGS_SIZE = 10;
    private final Faker faker = new Faker();

    private final List<String> categories = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();
    private Entry entry;
    private List<Entry> entries = new ArrayList<>();

    @Value("${config.first-page:0}")
    private Integer firstPage;
    @Value("${config.page-size:20}")
    private Integer pageSize;
    @Autowired
    private EntryRepository entryRepository;

    /**
     * Get the first page.
     *
     * @return first page
     */
    private Pageable getFirstPage() {
        return new PageRequest(firstPage, pageSize);
    }

    /**
     * Get element of list on index, if index its bigger than list size, index
     * is recalculate to avoid IndexOutOfBoundsException.
     *
     * @param list list of elements
     * @param index index to get
     * @return element on index
     */
    private String getWithIndex(List<String> list, int index) {
        int size = list.size();
        while (index >= size) {
            index -= size;
        }
        return list.get(index);
    }

    /**
     * Check if elements of page are sorted chronologicaly.
     *
     * @param result page to check chronology
     */
    private void checkChronology(Page<Entry> result) {
        Date date = new Date();
        for (Entry element : result.getContent()) {
            assertTrue(date.after(element.getDate()) || date.equals(element.getDate()));
            date = element.getDate();
        }
    }

    @Before
    public void setUp() {
        //Clean data
        entryRepository.deleteAll();
        categories.clear();
        tags.clear();
        entries.clear();
        //Populate data
        categories.addAll(faker.lorem().words(CATEGORIES_SIZE));
        tags.addAll(faker.lorem().words(TAGS_SIZE));
        for (int i = 0; i < ENTRIES_SIZE; i++) {
            Entry element = new Entry();
            element.setTitle(faker.lorem().sentence());
            element.setPath(toURL(element.getTitle()));
            element.setContent(faker.lorem().paragraph());
            element.setCategory(getWithIndex(categories, i));
            String tag1 = getWithIndex(tags, i);
            String tag2 = getWithIndex(tags, i + 1);
            String tag3 = getWithIndex(tags, i + 2);
            element.setTags(new HashSet<>(Arrays.asList(tag1, tag2, tag3)));

            entries.add(element);
        }
        entries = entryRepository.insert(entries);
        entry = entries.get(0);
//        entries.forEach(element -> System.out.println(element));
    }

    /**
     * Test of findByPath method, of class EntryRepository.
     */
    @Test
    public void testFindByPath() {
        System.out.println("EntryRepository.findByPath");
        Optional<Entry> result = entryRepository.findByPath(entry.getPath());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(equalTo(entry)));
    }

    /**
     * Test of findByPath method, of class EntryRepository.
     */
    @Test
    public void testFindByPath_NotPresent() {
        System.out.println("EntryRepository.findByPath (not present)");
        Optional<Entry> result = entryRepository.findByPath("not-present");
        assertThat(result.isPresent(), is(false));
    }

    /**
     * Test of findByOrderByDateDesc method, of class EntryRepository.
     */
    @Test
    public void testFindByOrderByDateDesc() {
        System.out.println("EntryRepository.findByOrderByDateDesc");
        Pageable pageable = getFirstPage();
        Page<Entry> result = entryRepository.findByOrderByDateDesc(pageable);
        assertThat(result, is(notNullValue(Page.class)));
        assertThat(result.hasContent(), is(true));
        assertThat(result.isFirst(), is(true));
        assertThat(result.hasNext(), is(true));
        assertThat(result.getNumber(), is(equalTo(firstPage)));
        assertThat(result.getNumberOfElements(), is(equalTo(pageSize)));
        assertThat(result.getTotalElements(), is(equalTo((long) ENTRIES_SIZE)));
        checkChronology(result);
    }

    /**
     * Test of findByCategoryOrderByDateDesc method, of class EntryRepository.
     */
    @Test
    public void testFindByCategoryOrderByDateDesc() {
        System.out.println("EntryRepository.findByCategoryOrderByDateDesc");
        String category = categories.get(0);
        Pageable pageable = getFirstPage();
        Page<Entry> result = entryRepository.findByCategoryOrderByDateDesc(category, pageable);
        assertThat(result, is(notNullValue(Page.class)));
        assertThat(result.hasContent(), is(true));
        assertThat(result, everyItem(hasProperty("category", equalTo(category))));
        checkChronology(result);
    }

    /**
     * Test of findByTagsOrderByDateDesc method, of class EntryRepository.
     */
    @Test
    public void testFindByTagsOrderByDateDesc() {
        System.out.println("EntryRepository.findByTagsOrderByDateDesc");
        String tag = tags.get(0);
        Pageable pageable = getFirstPage();
        Page<Entry> result = entryRepository.findByTagsOrderByDateDesc(tag, pageable);
        assertThat(result, is(notNullValue(Page.class)));
        assertThat(result.hasContent(), is(true));
        assertThat(result, everyItem(hasProperty("tags", hasItem(tag))));
        checkChronology(result);
    }

    /**
     * Test of findDistinctCategories method, of class EntryRepository.
     */
    @Test
    public void testFindDistinctCategories() {
        System.out.println("EntryRepository.findDistinctCategories");
        List<String> result = entryRepository.findDistinctCategories();
        assertThat(result, is(notNullValue(List.class)));
        assertThat(result, is(not(empty())));
        assertThat(result, hasItems(toStringArray(categories)));
    }

    /**
     * Test of findDistinctTags method, of class EntryRepository.
     */
    @Test
    public void testFindDistinctTags() {
        System.out.println("EntryRepository.findDistinctTags");
        List<String> result = entryRepository.findDistinctTags();
        assertThat(result, is(notNullValue(List.class)));
        assertThat(result, is(not(empty())));
        assertThat(result, hasItems(toStringArray(tags)));
    }

}
