package com.wzl;

import com.wzl.bean.Person;
import com.wzl.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试：
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot01HelloworldApplicationTests {



	Logger logger = LoggerFactory.getLogger(getClass());


	@Test
	public void contextLoads() {
		//可以调整输出的日志级别；日志就只会在这个级别以后的高级别生效
		//low->high : trace<debug<info<warn<error
		logger.trace("这是test日志");
		logger.debug("this is debug log");
		//Springboot默认info级别
		logger.info("this is info log");
		logger.warn("this is warn log");
		logger.error("this is error log");
	}


//	@Autowired
//	Person person;
//
//	@Autowired
//	ApplicationContext ioc;
//
//	@Test
//	public void testHelloService() {
//		Boolean b = ioc.containsBean("helloService");
//		System.out.println(b);
//	}
//
//	@Test
//	public void contextLoads() {
//		System.out.println(person);
//	}



}











