package com.bioTools.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.bioTools.demo.util.GetRandomStringService;

@Service
public class CheckCodeService {
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@Autowired
	GetRandomStringService getRandomStringService;
	
	private String emailServiceCode;
	
	public String sendCheckCode(String email){
		emailServiceCode = getRandomStringService.getRandomString(6);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("注册验证码");
		message.setText("注册验证码是：" + emailServiceCode);
		message.setFrom("1245929757@qq.com");
		message.setTo(email);
		mailSender.send(message);
		return emailServiceCode;
	}
	
	


}
