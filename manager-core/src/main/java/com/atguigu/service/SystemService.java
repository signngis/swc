package com.atguigu.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 系统服务
 * @author lfy
 *
 */
@Service
public class SystemService {
	
	@Value("${email.host}")
	private String emailHost;
	@Value("${email.username}")
	private String emailUser;
	@Value("${email.password}")
	private String emailPwd;
	
	@Value("${email.charset}")
	private String charset;
	
	public void sendEmail(String to,String content,String title) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		
		//1、连接james
		email.setAuthentication(emailUser, emailPwd);
		email.setHostName(emailHost);
		
		
		//2、配置邮件信息
		email.setCharset(charset);
		email.addTo(to);
		email.setSubject(title);
		email.setHtmlMsg(content);
		email.setFrom(emailUser);
		
		//3、发送邮件
		email.send();
	}
	

}
