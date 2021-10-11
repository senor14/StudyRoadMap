package domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "StudyRoadData")
public class StudyRoadData {

    @Id
    private String roadId;
    private String userUuid;
    private String publicYn;
    private String roadTitle;
    private String created;

}
