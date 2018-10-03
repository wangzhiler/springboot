package com.wzl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot09TaskApplicationTests {

	@Autowired
	JavaMailSenderImpl mailSender;

	@Test
	public void contextLoads() {

		SimpleMailMessage message = new SimpleMailMessage();
		//邮件设置
		message.setSubject("通知-test");
		message.setText("texttexttext");
		message.setFrom("wangzhile1997@126.com");
		message.setTo("wangzhile_1997@163.com");
		mailSender.send(message);
	}

	@Test
	public void test02() {
		//1. 创建一个复杂的消息邮件
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setSubject("helper--subject");
			helper.setText("<div>helper--text</div><b>aa</b>");
			helper.setFrom("wangzhile1997@126.com");
			helper.setTo("wangzhile_1997@163.com");

			//上传文件
			helper.addAttachment("1.jpg", new File("D:\\project\\springboot\\springboot-09-task\\src\\main\\resources\\static\\cat1.jpg"));

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
		}




	}

}
