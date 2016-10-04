package org.habv.mnemosyne.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Herman Barrantes
 * @since 03/10/2016
 */
public class EntryRepositoryImpl implements EntryRepositoryCustom {

    private static final String COLLECTION_NAME = "entries";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findDistinctCategories() {
        return mongoTemplate
                .getCollection(COLLECTION_NAME)
                .distinct("category");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findDistinctTags() {
        return mongoTemplate
                .getCollection(COLLECTION_NAME)
                .distinct("tags");
    }

}
