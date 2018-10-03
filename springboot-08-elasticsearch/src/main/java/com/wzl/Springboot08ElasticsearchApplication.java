package com.wzl;

import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Springboot 默认支持两种技术来和ES交互
 * 1. Jest(默认不生效)
 * 	需要导入jest工具包 (io.searchbox.client.JestClient)
 * 2. SpringData ElasticSearch
 * 	1) Client 结点信息clusterNodes; clusterName
 * 	2) ElasticsearchTemplate 操作es
 * 	3) 编写一个ElasticSearchRepository的子接口来操作es
 * 两种用法
 * 1. 编写一个ElasticSearchRepository的子接口
 *
 */

@SpringBootApplication
public class Springboot08ElasticsearchApplication {



	public static void main(String[] args) {
		SpringApplication.run(Springboot08ElasticsearchApplication.class, args);
	}
}
