package service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import persistence.mongo.IMongoUserMapper;
import service.IMongoUserService;


@Service("MongoUserService")
public class MongoUserService implements IMongoUserService {

	@Resource(name = "MongoUserMapper")
	IMongoUserMapper mongoUserMapper;
	
//	@Resource(name = "RedisNewsWordMapper")
//	IRedisNewsWordMapper redisNewsWordMapper;


	Logger log = Logger.getLogger(this.getClass());

	@Override
	public int insertUserInfo(Map<String, Object> pMap) throws Exception {
			String colNm = "user_info";
			mongoUserMapper.insertUserInfo(pMap, colNm);
		return 1;
	}

	@Override
	public List<Map<String, String>> getUserInfo(Map<String, Object> pMap) throws Exception {
			String colNm = "user_info";
		return mongoUserMapper.getUserInfo(pMap, colNm);
	}

	@Override
	public int deleteUser(Map<String, Object> pMap) {
		String colNm = "user_info";
		return mongoUserMapper.deleteUserInfo(pMap, colNm);
	}

	@Override
	public int editUserInfo(Map<String, Object> before_map, Map<String, Object> after_map) {
		String colNm = "user_info";
		return mongoUserMapper.editUserInfo(before_map, after_map, colNm);
	}
	
	
	

}