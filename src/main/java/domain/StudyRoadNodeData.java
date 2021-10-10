package domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "StudyRoadNodeData")
public class StudyRoadNodeData {

    @Id
    private String nodeId;
    private String roadId;
    private String group;
    private String nodeText;
    private String nodeLoc;
    private String nodeCategory;
    private String from;
    private String to;
}
