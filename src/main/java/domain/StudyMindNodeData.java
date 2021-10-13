package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString
@Document(collection = "StudyMindNodeData")
public class StudyMindNodeData {

    @Id
    private String mindId; // 마인드 아이디
    private String key; // 노드 키
//    private String userUuid; // 유저 UUID
    private String studyRoadId; // 스터디로드 아이디
    private String studyRoadNodeId; // 스터디로드 노드 아이디
    private String group; // 노드 or 엣지
    private String mindLabel; // 마인드 제목
    private String x; // 노드의 x 좌표
    private String y; // 노드의 y 좌표
    private String source; // 소스 - 노드자식
    private String target; // 타겟 - 노드부모
}
