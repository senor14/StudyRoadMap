package service.impl;

import dto.UserDTO;
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

    @Override
    public List<Map<String, String>> getUserInfo(Map<String, Object> uMap) {

        return userMapper.getUserInfo(uMap, colNm);
    }

    @Override
    public int UserSignUp(Map<String, Object> uMap) {

        return userMapper.insertUserInfo(uMap, colNm);
    }

    @Override
    public UserDTO idCheck(String userId) {
        return null;
    }

    @Override
    public UserDTO getUserEmail(UserDTO pDTO) {
        return null;
    }

    @Override
    public int reMakePW(UserDTO pDTO) {
        return 0;
    }
}
