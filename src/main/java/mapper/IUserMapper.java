package mapper;

import java.util.List;
import java.util.Map;

public interface IUserMapper {

    List<Map<String, String>> getUserInfo(Map<String, Object> uMap, String colNm);

    int insertUserInfo(Map<String, Object> uMap, String colNm);
}
