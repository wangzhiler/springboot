package com.wzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * 一、搭建基本环境
 * 1. 导入数据库文件，创建出department和employee表
 * 2. 创建javaBean封装数据
 * 3. 整合Mybatis操作数据库
 * 		1） 配置数据源
 * 		2)  使用注解版Mybatis
 * 			(1) @MapperScan指定扫描所有Mapper接口所在的包
 *
 * 	二、快速体验缓存
 * 		步骤：
 * 		1. 开启基于注解的缓存
 * 		2. 标注缓存注解
 * 			@Cacheable 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
 * 			@CacheEvict 清空缓存，可用于delete
 * 			@CachePut 保证方法被调用，又希望结果被缓存，可用于update
 *
 * 	默认使用的是ConcurrentMapCacheManager== ConcurrentMapCache; 将数据保存在ConcurrentMap<Object, Object>中
 * 	开发中使用缓存中间件; redis、memcached、ehcache
 *
 * 	三、整合Redis作为缓存
 * 	Redis是一个开源(BSD许可)的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件
 * 		1. 引入redis的starter
 * 		2. 配置redis
 * 		3. 测试缓存
 * 			原理：CacheManager===Cache 缓存组件来实际给缓存中存取数据
 * 			1) 引入redis的starter 容器中保存的是 RedisCacheManager
 * 			2) RedisCacheManager 帮我们创建RedisCache来作为缓存组件; RedisCache通过操作redis缓存数据
 * 			3) 默认保存数据 k-v 都是Object: 利用序列化保存; 如何保存json?
 * 				引入了redis的starter, cacheManager变为 RedisCacheManager
 * 				默认创建的RedisCacheManager 操作redis的时候使用的是 RedisTemplate<Object, Object>
 * 				RedisTemplate<Object,Object>默认使用jdk序列化机制
 * 			4) 自定义CacheManager
 */

@MapperScan("com.wzl.mapper")
@SpringBootApplication
@EnableCaching
public class Springboot06CacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot06CacheApplication.class, args);
	}
}
