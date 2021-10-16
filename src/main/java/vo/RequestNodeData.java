package vo;

import lombok.Data;

@Data
public class RequestNodeData {

    private String nodeId;
    private String roadId;
    private String canvasClass;
    private String category;
    private String key;
    private String laneKey;
    private String nodeText;
    private String diagramText;
    private String categoryText;
    private String laneText;
    private String isGroup;
    private String group;
    private String categoryColor;
    private String laneColor;
    private String size;
    private String loc;
    private String from;
    private String to;
    private String type;
}
