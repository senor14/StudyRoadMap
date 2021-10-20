package service.impl;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.IMailService;
import util.CmmUtil;

import util.EncryptUtil;


@Service("MailService")
public class MailService implements IMailService {

	private Logger log = Logger.getLogger(this.getClass());
	
	final String host = "smtp.gmail.com";
	final String user = "newshfkfk@gmail.com"; //보내는 사람 주소
	final String password = "ufrikazlauweveyy"; // 로그인을 위한 비밀번호

	@Override
	public int doSendMail(Map<String, String> pMap) throws Exception {
		log.info("Service.doSendMail 시작");
		
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// 이메일 객체생성하기
		int res = 1;

		if(pMap == null) {
			pMap = new HashMap<>();
		}
		
		String Email = CmmUtil.nvl(pMap.get("userEmail"));

		Properties props = new Properties();
		
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		
		
		try {
			Session session = Session.getDefaultInstance(props,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, password);
						}
					});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));

			message.setSubject("[RoadMap team] 인증코드 안내");
			message.setText("RoadMap 인증코드는 " + pMap.get("authNum") + " 입니다.");
			
			Transport.send(message);
			
		} catch (MessagingException e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		} catch (Exception e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		}
		
		
		log.info("Service.doSendMail 종료");
		return res;
	}

	@Override
	public int doSendPassWordMail(Map<String, String> uMap) throws Exception{
		log.info(this.getClass().getName() + "doSendPassWordMail start");
		int res = 1;
		
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// 이메일 객체생성하기

		if(uMap == null) {
			uMap = new HashMap<>();
		}
		
		String Email = CmmUtil.nvl(uMap.get("userEmail"));

		Properties props = new Properties();
		
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		
		
		try {
			Session session = Session.getDefaultInstance(props,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, password);
						}
					});
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));

			message.setSubject("[RoadMap team] 임시 비밀번호 안내");
			message.setText("RoadMap 임시 비밀번호는 " + uMap.get("userPwd") + " 입니다. 보안을 위해 로그인 후 비밀번호를 재설정 해주세요.");
			
			Transport.send(message);
			
		} catch (MessagingException e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		} catch (Exception e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		}
		
		
		log.info(this.getClass().getName() + "doSendPassWordMail end");
		
		return res;
	}

	@Override
	public int doSendIdMail(Map<String, String> pMap) {

		log.info(this.getClass().getName() + "doSendIdMail start");

		int res = 1;

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// 이메일 객체생성하기

		if(pMap == null) {
			pMap = new HashMap<>();
		}


		String Email = CmmUtil.nvl(pMap.get("userEmail"));

		Properties props = new Properties();

		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");


		try {
			Session session = Session.getDefaultInstance(props,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, password);
						}
					});
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));

			message.setSubject("[RoadMap team] 아이디 안내");
			message.setText("RoadMap 아이디는 " + pMap.get("userId") + " 입니다.");

			Transport.send(message);

		} catch (MessagingException e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		} catch (Exception e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		}


		log.info(this.getClass().getName() + "doSendIdMail end");

		return res;
	}


}
