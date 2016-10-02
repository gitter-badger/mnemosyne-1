package org.habv.mnemosyne.repository;

import org.habv.mnemosyne.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@RepositoryRestResource(collectionResourceRel = "entries", path = "entries")
public interface EntryRepository extends MongoRepository<Entry, String> {

    @RestResource(path = "entries", rel = "entries")
    Page<Entry> findByOrderByDateDesc(Pageable pageable);
    
    @RestResource(path = "entriesByCategory", rel = "entriesByCategory")
    Page<Entry> findByCategoryOrderByDateDesc(@Param("category") String category, Pageable pageable);
    
    @RestResource(path = "entriesByTag", rel = "entriesByTag")
    Page<Entry> findByTagsOrderByDateDesc(@Param("tag") String tag, Pageable pageable);
}
