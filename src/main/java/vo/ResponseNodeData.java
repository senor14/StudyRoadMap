package vo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class ResponseNodeData {

    private String nodeMindId; // 노드 마인드 id
    private String edgeMindId;  // 엣지 마인드 id
    private String roadId;
    private String nodeId;
    private String mindLabel; // 마인드 제목
    private String x; // x 좌표
    private String y; // y 좌표
    private String source; // 소스 - 노드자식
    private String target; // 타겟 - 노드부모
}
