package persistence.mongo;

import dto.StudyRoadInfoDTO;

import java.util.List;
import java.util.Map;

public interface IStudyMindMapper {


    public List<Map<String, String>> getMindMap(String colNm, StudyRoadInfoDTO pDTO) throws Exception;

    public List<Map<String, String>> getMindMapNode(String colNm, StudyRoadInfoDTO pDTO) throws Exception;


    public int insertDefaultMindMap(Map<String, Object> pMap, String colNm) throws Exception;

    /**
     * 루트 노드 및 마인드 정보 저장
     * ex) 네트워크 스터디로드맵 노드 관련 마인드맵의 루트 -> 네트워크
     *
     * @param nMap 저장할 노드 정보
     * @param mMap 저장할 마인드 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     * @throws Exception
     */
    public int insertRootNode(Map<String, Object> nMap, Map<String, String> mMap, String colNm) throws Exception;

    /**
     * 스터디마인드맵 mind_data 에 데이터 추가
     *
     * @param pMap  저장할 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     * @throws Exception
     */
//    public int insertMindData(Map<String, String> pMap, String colNm) throws Exception;

    // (저장버튼 누르면) 스터디 노드 데이터 modal-id, nodes-data-id 저장


    // 스터디마인드맵 모달 수정


    // 스터디마인드맵 모달 전체 조회


    // 스터디마인드맵 개별 모달 조회

    /**
     * 스터디 노드 추가(node_data - nodes 에 데이터 저장
     *
     * @param pMap 저장 정보
     * @param colNm 저장할 컬렉션
     * @return 저장 결과
     * @throws Exception
     */
//    public int insertMindNode(Map<String, Object> pMap, String colNm) throws Exception;


    // 스터디 노드 전체 조회(node_data - nodes 데이터 전부 조회)


    // 스터디 노드 삭제


    // 스터디 노드 삭제

}