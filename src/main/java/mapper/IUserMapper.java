package mapper;

import java.util.List;
import java.util.Map;

public interface IUserMapper {

    List<Map<String, String>> getUserInfo(Map<String, Object> uMap, String colNm);

    int insertUserInfo(Map<String, Object> uMap, String colNm);

    int getUserEmail(Map<String, Object> uMap, String colNm);

    int insertAuthNum(Map<String, Object> pMap, String colNm);

    int idCheck(Map<String, Object> uMap, String colNm);

    int getAuthNum(Map<String, Object> uMap, String auth_colNm);

    int reMakePW(Map<String, Object> beforeMap, Map<String, Object> afterMap, String colNm);

    List<Map<String, Object>> getDeleteUserInfo(Map<String, Object> uMap, String colNm);

    int deleteCareerRoadMap(Map<String, Object> pMap, String career_colNm);

    int deleteStudyMinddMap(Map<String, Object> pMap, String mind_colNm);

    int deleteStudyRoadMap(Map<String, Object> pMap, String road_colNm);

    int passWordChange(Map<String, Object> beforeMap, Map<String, Object> afterMap, String colNm);

    int deleteAuthNum(Map<String, Object> pMap, String auth_colNm);

    List<Map<String, String>> getUserId(Map<String, Object> uMap, String user_colNm);
}
