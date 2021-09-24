package service;

import java.util.List;
import java.util.Map;

public interface IMongoScheduleService {

	public int insertSchedule(Map<String, Object> pMap);

	public List<Map<String, String>> getSchedule(Map<String, Object> pMap);

	public int deleteSchedule(Map<String, Object> pMap);

	public int updateSchedule(Map<String, Object> before_map, Map<String, Object> after_map);
	
}
