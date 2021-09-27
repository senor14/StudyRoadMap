package service;


import dto.UserDTO;

public interface IMailService {

	int doSendMail(UserDTO uDTO) throws Exception;

	int doSendPassWordMail(UserDTO uDTO) throws Exception;
}
