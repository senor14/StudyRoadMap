package dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StudyMindmapDTO {

	private String user_uuid; // 유저 UUID
	private String studyroad_id; // 스터디로드 아이디
	private String studyroad_node_id; // 스터디로드 노드 아이디
	private String studyroad_node_title; // 스터디로드 노드 제목
	private String created;
	private List<Map<String, String>> modals;
	private Map<String, List<String>> node_data;
	private String publicYn; // 공개여부
}
