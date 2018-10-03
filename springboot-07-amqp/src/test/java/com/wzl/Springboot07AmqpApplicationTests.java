package com.wzl;

import com.wzl.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot07AmqpApplicationTests {


	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	AmqpAdmin amqpAdmin;


	@Test
	public void createExchange() {
//		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));

//		amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));

		//创建绑定规则
		amqpAdmin.declareBinding(new Binding("amqpadmin.queue"
				, Binding.DestinationType.QUEUE, "amqpadmin.exchange"
				, "amqp.haha", null));

		System.out.println("创建完成");
	}


	/**
	 * 1. 单播(点对点)
	 */
	@Test
	public void contextLoads() {
		//Message需要自己构造; 可以定制消息内容和头
//		rabbitTemplate.send(exchange, routekey, message);

		//只需要传入要发送的对象, 自动序列化发送给rabbitMQ
		//object默认被当成消息体
//		rabbitTemplate.convertAndSend(exchange, routekey, object);

		Map<String, Object> map = new HashMap<>();
		map.put("msg", "第一个消息");
		map.put("data", Arrays.asList("helloworld", 123, true));
		rabbitTemplate.convertAndSend("exchange.direct", "wzl.news", new Book("yyyyy","www"));
	}

	//接受消息
	@Test
	public void receive() {
		Object o = rabbitTemplate.receiveAndConvert("wzl.news");
		System.out.println(o.getClass());
		System.out.println(o);
	}


	/**
	 * 广播
	 */
	@Test
	public void sendMsg() {
		rabbitTemplate.convertAndSend("exchange.fanout", "", new Book("qwertyuio", "zxcvbn"));
	}


}
