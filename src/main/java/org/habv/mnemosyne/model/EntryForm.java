package org.habv.mnemosyne.model;

import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 24/10/2016
 */
@Data
public class EntryForm {

    public static final String ENTRY = "entry";
    public static final String ENTRIES = "entries";

    @NotEmpty(message = "{entry.title.not_empty}")
    private String title;
    @NotEmpty(message = "{entry.content.not_empty}")
    private String content;
    @NotEmpty(message = "{entry.category.not_empty}")
    private String category;
    @NotEmpty(message = "{entry.tags.not_empty}")
    private Set<String> tags;
}
