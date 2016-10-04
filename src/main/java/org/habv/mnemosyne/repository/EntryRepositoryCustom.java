package org.habv.mnemosyne.repository;

import java.util.List;

/**
 * @author Herman Barrantes
 * @since 03/10/2016
 */
public interface EntryRepositoryCustom {

    List<String> findDistinctCategories();

    List<String> findDistinctTags();

}
