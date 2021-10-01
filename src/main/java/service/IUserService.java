package service;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<Map<String, String>> getUserInfo(Map<String, Object> uMap);

    int UserSignUp(Map<String, Object> uMap);

    int idCheck(Map<String, Object> uMap);

    int getUserEmail(Map<String, Object> uMap);

    int reMakePW(Map<String, Object> beforeMap, Map<String, Object> afterMap);

    int insertAuthNum(Map<String, Object> pMap);

    int getAuthNum(Map<String, Object> uMap);
}
