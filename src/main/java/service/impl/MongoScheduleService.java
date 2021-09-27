package service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import persistence.mongo.IMongoScheduleMapper;
import service.IMongoScheduleService;


@Service("MongoScheduleService")
public class MongoScheduleService implements IMongoScheduleService {

	@Resource(name = "MongoScheduleMapper")
	IMongoScheduleMapper mongoScheduleMapper;

	Logger log = Logger.getLogger(this.getClass());

	
	@Override
	public int insertSchedule(Map<String, Object> pMap) {
		String colNm = "user_schedule";
		return mongoScheduleMapper.insertSchedule(pMap, colNm);
	}


	@Override
	public List<Map<String, String>> getSchedule(Map<String, Object> pMap) {
		String colNm = "user_schedule";
		return mongoScheduleMapper.getSchedule(pMap, colNm);
	}


	@Override
	public int deleteSchedule(Map<String, Object> pMap) {
		String colNm = "user_schedule";
		return mongoScheduleMapper.deleteSchedule(pMap, colNm);
	}


	@Override
	public int updateSchedule(Map<String, Object> before_map, Map<String, Object> after_map) {
		String colNm = "user_schedule";
		return mongoScheduleMapper.updateSchedule(before_map, after_map, colNm);
	}

	
	
	

}