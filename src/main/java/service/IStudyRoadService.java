package service;

import domain.*;

import java.util.List;

public interface IStudyRoadService {

    // 로드 id로 로드맵데이터 정보 가져오기
    public StudyRoadData getRoadMapData(String roadId) throws Exception;

    // 노드 id로 로드맵 노드 정보 가져오기
    public StudyRoadNodeData getRoadMapNodeData(String nodeId) throws Exception;

    // publicYn이 Y인 목록중에서 StudyRoadData-roadTitle 가 검색한 정보와 일치/포함되는
    // 로드맵데이터 리스트 가져오기
    public List<StudyRoadData> getPublicRoadTitle(String roadTitle) throws Exception;

    // publicYn이 Y인 목록중에서 StudyRoadNodeData-nodeCategory 가 검색한 정보가 포함되는
    // nodeId 리스트 반환
    public List<String> getRoadIdsByCategory(String nodeCategory) throws Exception;

    // publicYn이 Y인 목록중에서 getRoadIdsByNodeCategory 로 검색하고 나온 nodeId 로
    // 로드맵데이터 리스트 가져오기
    public List<StudyRoadData> getPublicCategory(List<String> nodeIds) throws Exception;

    // 로드 id로 로드노드 리스트 가져오기
    public List<StudyRoadNodeData> getRoadMapNodeByRoadId(String roadId) throws Exception;

    // 유저 id로 로드맵 리스트 가져오기
    public List<StudyRoadData> getRoadDataByUserUuid(String userUuid) throws Exception;

    // StudyRoadData 삽입
    public int insertRoadData(StudyRoadData roadData) throws Exception;

    // StudyRoadNodeData 삽입
    public int insertRoadNode(StudyRoadNodeData nodeData) throws Exception;

    // StudyRoadData 수정
    public int updateRoadData(StudyRoadData roadData) throws Exception;

    // StudyRoadNodeData 수정
    public int updateRoadNodeData(StudyRoadNodeData nodeData) throws Exception;

    // StudyRoadData 삭제
    public int deleteRoadData(String roadId) throws Exception;

    // StudyRoadNodeData 삭제
    public int deleteRoadNode(String nodeId) throws Exception;

}
