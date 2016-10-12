package org.habv.mnemosyne.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Herman Barrantes
 * @since 04/08/2016
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Document(collection = User.COLLECTION_NAME)
public class User {

    public static final String COLLECTION_NAME = "users";

    @Id
    private String id;
    private String name;
    @Indexed
    private String email;
    private String password;
    private List<String> roles;
    private boolean enabled;
}
