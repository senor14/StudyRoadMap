package dto;

public class UserDTO {
	private String user_id; // 아이디
	private String user_pwd; // 패스워드
	private String user_email; // 이메일
	private String user_interest; // 중요물품
	private String user_authNum; // 이메일 인증코드

	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_interest() {
		return user_interest;
	}
	public void setUser_interest(String user_interest) {
		this.user_interest = user_interest; 
	}
	public String getUser_authNum() {
		return user_authNum;
	}
	public void setUser_authNum(String user_authNum) {
		this.user_authNum = user_authNum;
	}
	


}
