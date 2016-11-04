package org.habv.mnemosyne.model;

import java.util.Date;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import static org.habv.mnemosyne.model.Entry.COLLECTION_NAME;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 04/08/2016
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Document(collection = COLLECTION_NAME)
public class Entry {

    public static final String COLLECTION_NAME = "entries";

    @Id
    private String id;
    private String title;
    @Indexed(unique = true)
    private String path;
    private String content;
    @Indexed
    private String category;
    @Indexed
    private Set<String> tags;
    //Auditing
    @CreatedBy
    private String author;
    @CreatedDate
    private Date date;
    @LastModifiedDate
    private Date updatedDate;
    @LastModifiedBy
    private String updatedBy;
}
