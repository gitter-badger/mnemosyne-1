package org.habv.mnemosyne.repository;

import java.util.Optional;
import org.habv.mnemosyne.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 04/08/2016
 */
public interface EntryRepository extends MongoRepository<Entry, String>, EntryRepositoryCustom {

    Optional<Entry> findByPath(String path);

    Page<Entry> findByOrderByDateDesc(Pageable pageable);

    Page<Entry> findByCategoryOrderByDateDesc(String category, Pageable pageable);

    Page<Entry> findByTagsOrderByDateDesc(String tag, Pageable pageable);
}
