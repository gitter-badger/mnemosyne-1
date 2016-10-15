package org.habv.mnemosyne.service;

import java.util.List;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.repository.EntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 14/10/2016
 */
@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Entry insert(Entry entry) {
        return entryRepository.insert(entry);
    }

    public Entry save(Entry entry) {
        return entryRepository.save(entry);
    }

    public Page<Entry> findByOrderByDateDesc(Pageable pageable) {
        return entryRepository.findByOrderByDateDesc(pageable);
    }

    public Page<Entry> findByCategoryOrderByDateDesc(String category, Pageable pageable) {
        return entryRepository.findByCategoryOrderByDateDesc(category, pageable);
    }

    public Page<Entry> findByTagsOrderByDateDesc(String tag, Pageable pageable) {
        return entryRepository.findByTagsOrderByDateDesc(tag, pageable);
    }

    public List<String> findDistinctCategories() {
        return entryRepository.findDistinctCategories();
    }

    public List<String> findDistinctTags() {
        return entryRepository.findDistinctTags();
    }
}