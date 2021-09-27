package persistence.mongo;

import java.util.List;
import java.util.Map;

public interface IMongoUserMapper {

	public int insertUserInfo(Map<String, Object> pMap, String colNm) throws Exception;

	public List<Map<String, String>> getUserInfo(Map<String, Object> pMap, String colNm) throws Exception;

	public int deleteUserInfo(Map<String, Object> pMap, String colNm);

	public int editUserInfo(Map<String, Object> before_map, Map<String, Object> after_map, String colNm);
}
