package org.habv.mnemosyne.repository;

import org.habv.mnemosyne.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
public interface EntryRepository extends MongoRepository<Entry, String>, EntryRepositoryCustom {

    Page<Entry> findByOrderByDateDesc(Pageable pageable);

    Page<Entry> findByCategoryOrderByDateDesc(String category, Pageable pageable);

    Page<Entry> findByTagsOrderByDateDesc(String tag, Pageable pageable);
}
