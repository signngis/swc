package com.atguigu.test;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

public class EmailTest {
	
	
	@Test
	public void testSendEmail() throws Exception {
		
		//1、创建一个简单邮件
		SimpleEmail email = new SimpleEmail();
		
		//2、邮件内容
		email.setSubject("开会通知");//标题
		email.setMsg("今晚10点开会;这是系统邮件请勿回复");//内容
		email.addTo("17512080612@163.com");//收件人地址
		email.setFrom("admin@atguigu.com");
		
		//3、配置邮件服务器信息
		//email.setHostName("smtp.163.com");//设置邮件服务器的地址
		email.setHostName("127.0.0.1");
		//独立密码，为了安全期间其他连接邮件服务器的客户端都需要使用自己的独立密码；
		//email.setAuthentication("17512080612@163.com", "l123456");
		email.setAuthentication("admin@atguigu.com", "123456");
		//email.setSSL(true);  QQ邮箱需要使用安全连接登陆；
		//4、发送邮件
		email.send();
		
		//5、如何发送带附件的，带图片的等；
		
		
	}

}
