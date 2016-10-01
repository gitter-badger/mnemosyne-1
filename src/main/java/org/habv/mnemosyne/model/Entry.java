package org.habv.mnemosyne.model;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Data
@Document(collection = "entries")
public class Entry {

    @Id
    private String id;
    private String title;
    private String content;
    private Date date;
    @Indexed
    private String category;
    @Indexed
    private List<String> tags;
}
