package org.habv.mnemosyne.repository;

import org.habv.mnemosyne.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Herman Barrantes
 * @since 04/08/2016
 */
public interface EntryRepository extends MongoRepository<Entry, String> {
}
