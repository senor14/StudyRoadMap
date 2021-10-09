package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@ToString
@Document(collection = "StudyMindData")
public class StudyMindDTO {

	private String userUuid; // 유저 UUID
	private String studyRoadId; // 스터디로드 아이디
	private String studyRoadNodeId; // 스터디로드 노드 아이디
	private String mindId; // 마인드 아이디
	private String mindLabel; // 마인드 제목
	private String mindContents; // 마인드 내용
	private String url; // 참조 주소
	private String bookTitle; // 참조 서적
	private String bookLink; // 참조 서적 링크
	private String created; // 생성시간

}
