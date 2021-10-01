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

    public static String colNm = "User";
    public static String auth_colNm = "email_auth";

    @Override
    public List<Map<String, String>> getUserInfo(Map<String, Object> uMap) {

        return userMapper.getUserInfo(uMap, colNm);
    }

    @Override
    public int UserSignUp(Map<String, Object> uMap) {

        return userMapper.insertUserInfo(uMap, colNm);
    }

    @Override
    public int idCheck(Map<String, Object> uMap) {

        return userMapper.idCheck(uMap, colNm);
    }

    @Override
    public int getUserEmail(Map<String, Object> uMap) {

        return userMapper.getUserEmail(uMap, colNm);

    }

    @Override
    public int reMakePW(Map<String, Object> beforeMap, Map<String, Object> afterMap) {

        return userMapper.reMakePW(beforeMap, afterMap, colNm);
    }

    @Override
    public int insertAuthNum(Map<String, Object> pMap) {

        return userMapper.insertAuthNum(pMap, auth_colNm);
    }

    @Override
    public int getAuthNum(Map<String, Object> uMap) {

        return userMapper.getAuthNum(uMap, auth_colNm);
    }
}
