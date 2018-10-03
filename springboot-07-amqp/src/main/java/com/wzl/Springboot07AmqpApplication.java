package com.wzl;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 自动配置
 * 1. RabbitAutoConfiguration
 * 2. CachingConnectionFactory 自动配置的连接工厂
 * 3. RabbitProperties 封装了RabbitMQ配置
 * 4. RabbitTemplate 给RabbitMQ发送和接受消息
 * 5. AmqpAdmin RabbitMQ的系统管理组件
 * 	 	创建和删除 queue exchange binding
 * 6. @EnableRabbit + @RabbitListener 监听消息队列内容
 */


@EnableRabbit //开启基于注解的RabbitMQ模式
@SpringBootApplication
public class Springboot07AmqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot07AmqpApplication.class, args);
	}
}
