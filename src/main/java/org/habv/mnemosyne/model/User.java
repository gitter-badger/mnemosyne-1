package org.habv.mnemosyne.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Data
@Document
public class User {

    @Id
    private String id;
    private String name;
    private String email;
}
