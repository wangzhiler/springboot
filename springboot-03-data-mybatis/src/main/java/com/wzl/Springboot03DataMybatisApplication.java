package com.wzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.wzl.mapper")
@SpringBootApplication
public class Springboot03DataMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot03DataMybatisApplication.class, args);
	}
}
