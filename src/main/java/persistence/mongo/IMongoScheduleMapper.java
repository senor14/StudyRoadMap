package persistence.mongo;

import java.util.List;
import java.util.Map;

public interface IMongoScheduleMapper {

	public int insertSchedule(Map<String, Object> pMap, String colNm);

	public List<Map<String, String>> getSchedule(Map<String, Object> pMap, String colNm);

	public int deleteSchedule(Map<String, Object> pMap, String colNm);

	public int updateSchedule(Map<String, Object> before_map, Map<String, Object> after_map, String colNm);
}
