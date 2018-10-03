package com.wzl;

import com.wzl.bean.Employee;
import com.wzl.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot06CacheApplicationTests {

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate; //操作k-v都是字符串

	@Autowired
	RedisTemplate redisTemplate; //k-v都是对象的

	@Autowired
	RedisTemplate<Object, Employee> empRedisTemplate;


	/**
	 * Redis常见的五大数据类型
	 * String(字符串)
	 * 		stringRedisTemplate.opsForValue()
	 * List(列表)
	 * 		stringRedisTemplate.opsForList()
	 * Set(集合)
	 * 		stringRedisTemplate.opsForSet()
	 * Hash(散列)
	 * 		stringRedisTemplate.opsForHash()
	 * ZSet(有序集合)
	 * 		stringRedisTemplate.opsForZSet()
	 */
	@Test
	public void test01() {
		//给redis保存数据
//		stringRedisTemplate.opsForValue().append("msg", "hello");
		String msg = stringRedisTemplate.opsForValue().get("msg");
		System.out.println(msg);

		stringRedisTemplate.opsForList().leftPush("mylist", "1");
		stringRedisTemplate.opsForList().leftPush("mylist", "4");
		stringRedisTemplate.opsForList().leftPush("mylist", "3");
		stringRedisTemplate.opsForList().leftPush("mylist", "3");
	}


	//测试保存对象
	@Test
	public void test02() {
		//默认如果保存对象,使用jdk序列化机制，序列化后的数据保存到redis中
//		redisTemplate.opsForValue().set("emp-01", employeeMapper.getEmpById(3));
		//1. 以json方式保存
		// (1)自己将对象转为json
		// (2)redisTemplate默认的序列化规则
		empRedisTemplate.opsForValue().set("emp-03", employeeMapper.getEmpById(3));
	}

	@Test
	public void contextLoads() {

		Employee empById = employeeMapper.getEmpById(1);
		System.out.println(empById);
	}

}
