package domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "StudyRoadDiagramData")
public class StudyRoadDiagramData {

    @Id
    private String diagramId;
    private String roadId;
    private String laneWidth;
    private String key;
    private String text;
    private String color;
    private String size;
    private String loc;
    private String created;


}
