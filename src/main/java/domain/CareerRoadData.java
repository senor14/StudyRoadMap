package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString
@Document(collection = "CareerRoadData")
public class CareerRoadData {

    @Id
    private String careerRoadNodeId; // 커리어 노드 아이디
    private String userUuid; // 유저 UUID (=커리어 로드맵 아이디)
    private String nodeYear; // 년
    private String nodeMonth; // 월
    private String nodeDay; // 일
    private String nodeContent; // 내용
    private String nodeType; // 유형
    private Boolean importance; // 중요
    private String date; // 년-월-일
}
