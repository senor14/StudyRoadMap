package domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "StudyRoadNodeData")
public class StudyRoadNodeData {

    @Id
    private String nodeId;
    private String roadId;
    private String canvasClass;
    private String category;
    private String key;
    private String text;
    private String isGroup;
    private String group;
    private String color;
    private String size;
    private String loc;
    private String from;
    private String to;

}
