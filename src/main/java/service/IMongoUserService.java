package service;

import java.util.List;
import java.util.Map;

public interface IMongoUserService {
	
	public int insertUserInfo(Map<String, Object> pMap) throws Exception;

	public List<Map<String, String>> getUserInfo(Map<String, Object> pMap) throws Exception;

	public int deleteUser(Map<String, Object> pMap);

	public int editUserInfo(Map<String, Object> before_map, Map<String, Object> after_map);

}
