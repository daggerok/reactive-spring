package daggerok.domain.user.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = -5252172190065816437L;

    @Id
    String id;
    String username;
}
