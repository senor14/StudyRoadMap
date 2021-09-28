package service;

import dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<Map<String, String>> getUserInfo(Map<String, Object> uMap);

    int UserSignUp(Map<String, Object> uMap);

    UserDTO idCheck(String userId);

    UserDTO getUserEmail(UserDTO pDTO);

    int reMakePW(UserDTO pDTO);


}
