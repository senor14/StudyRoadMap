package service;



import java.util.Map;

public interface IMailService {

	int doSendMail(Map<String, String> pMap) throws Exception;

	int doSendPassWordMail(Map<String, String> uMap) throws Exception;

	int doSendIdMail(Map<String, String> pMap);

}
