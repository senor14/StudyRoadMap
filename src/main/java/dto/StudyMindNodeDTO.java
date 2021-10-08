package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "StudyMindNodeData")
public class StudyMindNodeDTO {

    private String userUuid;
    private String studyRoadId;
    private String studyRoadNodeId;
    private String mindId;
    private String group;
    private String mindLabel;

}
