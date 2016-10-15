package org.habv.mnemosyne.repository;

import java.util.List;
import static org.habv.mnemosyne.model.Entry.COLLECTION_NAME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 03/10/2016
 */
public class EntryRepositoryImpl implements EntryRepositoryCustom {

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
