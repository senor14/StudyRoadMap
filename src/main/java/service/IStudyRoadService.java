package service;

import domain.StudyRoadData;
import domain.StudyRoadDiagramData;
import domain.StudyRoadNodeData;

import java.util.List;

public interface IStudyRoadService {

    // 로드 id로 로드맵데이터 정보 가져오기
    public StudyRoadData getRoadMapData(String roadId) throws Exception;

    // publicYn이 Y인 목록중에서 StudyRoadData-roadTitle 가 검색한 정보와 일치/포함되는
    // 로드맵데이터 리스트 가져오기 Distinct 로
    public List<StudyRoadData> getPublicRoadTitle(String roadTitle) throws Exception;

    // publicYn이 Y인 목록중에서 StudyRoadNodeData-nodeCategory 가 검색한 정보가 포함되는
    // 로드맵데이터 리스트 가져오기 Distinct 로

    // 로드 id로 로드다이어그램 리스트 가져오기
    public List<StudyRoadDiagramData> getRoadMapDiagram(String roadId) throws Exception;

    // 로드 id로 로드노드 리스트 가져오기
    public List<StudyRoadNodeData> getRoadMapNode(String roadId) throws Exception;

    // 유저 id로 로드맵 리스트 가져오기

    // StudyRoadData 삽입

    // StudyRoadDiagramData 삽입

    // StudyRoadNodeData 삽입

    // StudyRoadData 수정

    // StudyRoadDiagramData 수정

    // StudyRoadNodeData 수정

    // StudyRoadData 삭제

    // StudyRoadDiagramData 삭제

    // StudyRoadNodeData 삭제

}
