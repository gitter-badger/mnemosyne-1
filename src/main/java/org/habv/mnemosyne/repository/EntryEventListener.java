package org.habv.mnemosyne.repository;

import org.habv.mnemosyne.model.Entry;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 18/10/2016
 */
@Component
public class EntryEventListener extends AbstractMongoEventListener<Entry> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Entry> event) {
        Entry entry = event.getSource();
        String path = entry
                .getTitle()
                .trim()//remove empty spaces
                .replaceAll("[^A-Za-z0-9]+", "-")//replace all special characters with guion
                .replaceAll("(^-|-$)", "")//remove first and last guion
                .toLowerCase();
        entry.setPath(path);
    }

}
