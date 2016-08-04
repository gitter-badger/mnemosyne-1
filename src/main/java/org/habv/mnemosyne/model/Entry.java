package org.habv.mnemosyne.model;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Data
@Document
public class Entry {

    @Id
    private String id;
    private String title;
    private String content;
    private Date date;
    private String category;
    private List<String> tags;
}
