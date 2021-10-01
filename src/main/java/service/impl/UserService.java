package service.impl;

import mapper.IUserMapper;
import org.springframework.stereotype.Service;
import service.IUserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service("UserService")
public class UserService implements IUserService {

    @Resource(name = "UserMapper")
    IUserMapper userMapper;

    public static String user_colNm = "User";
    public static String auth_colNm = "email_auth";
    public static String career_colNm = "CareerRoadMap";
    public static String mind_colNm = "StudyMindMap";
    public static String road_colNm = "StudyRoadMap";

    @Override
    public List<Map<String, String>> getUserInfo(Map<String, Object> uMap) {

        return userMapper.getUserInfo(uMap, user_colNm);
    }

    @Override
    public int UserSignUp(Map<String, Object> uMap) {

        return userMapper.insertUserInfo(uMap, user_colNm);
    }

    @Override
    public int idCheck(Map<String, Object> uMap) {

        return userMapper.idCheck(uMap, user_colNm);
    }

    @Override
    public int getUserEmail(Map<String, Object> uMap) {

        return userMapper.getUserEmail(uMap, user_colNm);

    }

    @Override
    public int reMakePW(Map<String, Object> beforeMap, Map<String, Object> afterMap) {

        return userMapper.reMakePW(beforeMap, afterMap, user_colNm);
    }

    @Override
    public int insertAuthNum(Map<String, Object> pMap) {

        return userMapper.insertAuthNum(pMap, auth_colNm);
    }

    @Override
    public int getAuthNum(Map<String, Object> uMap) {

        return userMapper.getAuthNum(uMap, auth_colNm);
    }

    @Override
    public List<Map<String, Object>> getDeleteUserInfo(Map<String, Object> uMap) {

        return userMapper.getDeleteUserInfo(uMap, user_colNm);
    }

    @Override
    public int deleteCareerRoadMap(Map<String, Object> pMap) {

        return userMapper.deleteCareerRoadMap(pMap, career_colNm);
    }

    @Override
    public int deleteStudyMinddMap(Map<String, Object> pMap) {

        return userMapper.deleteStudyMinddMap(pMap, mind_colNm);
    }

    @Override
    public int deleteStudyRoadMap(Map<String, Object> pMap) {

        return userMapper.deleteStudyRoadMap(pMap, road_colNm);
    }

    @Override
    public int passWordChange(Map<String, Object> beforeMap, Map<String, Object> afterMap) {

        return userMapper.passWordChange(beforeMap, afterMap, user_colNm);
    }
}
