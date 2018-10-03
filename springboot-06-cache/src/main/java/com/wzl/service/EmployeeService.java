package com.wzl.service;

import com.wzl.bean.Employee;
import com.wzl.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * Created by thinkpad on 2018/10/1.
 */

//@CacheConfig 指定全部的公共属性
@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不在调用方法
     *
     * CacheManager 管理多个缓存组件，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
     *
     *  原理：
     *  1. 自动配置类：CacheAutoConfiguration
     *  2. 缓存配置类
     *  org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *  3. 哪个配置类默认生效？
     *  默认SimpleCacheConfiguration matched
     *  4. 给容器中注册了一个CacheManager: ConcurrentMapCacheManager
     *  5. 可以获取和创建ConcurrentMapCache类型的缓存组件;
     *      作用是将数据保存在ConcurrentMap里
     *
     *
     *  运行流程：
     *  @Cacheable
     *  1. 方法运行之前,先去查询Cache(缓存组件), 按照cacheNames指定的名字获取
     *      (CacheManager先获取相应的缓存), 第一次获取缓存如果没有Cache组件,会自动创建
     *  2. 取Cache中查找缓存的内容, 使用一个key, 默认是方法的参数;
     *      key是按照某种策略生成的; 默认是使用keyGenerator, 默认SimpleKeyGenerator生成key
     *          生成key策略: 没有参数 key=new SimpleKey();
     *                      一个参数 key=参数的值
     *                      多个参数 key=new SimpleKey(params);
     *  3. 没有查到缓存就调用目标方法;
     *  4. 将目标方法返回结果放入缓存
     *
     *  @Cacheable 标注的方法执行之前先检查缓存中有无该数据，默认按照参数的值作为key去查询缓存
     *  如果没有就运行方法并将结果放入缓存; 以后再来调用就可以直接使用缓存中的数据
     *
     *  核心:
     *      1). 使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     *      2). key使用SimpleKeyGenerator
     *
     *
     *
     * 几个属性：
     *  1. cacheNames/value: 指定缓存组件的名字; 将方法的返回结果放在哪个缓存中,可以是数组的形式,可以指定多个缓存
     *          eg. cacheNames="emp"  cacheNames={"emp", "tmp"}
     *
     *  2. key: 缓存数据的key; 可以用这个指定，默认使用方法参数的值 1-方法的返回值
     *          编写SpEL, eg.想取参数id的值 可以写成 #id #root.args[0] #a0
     *                      key="#root.methodName+'['+#id+']'"
     *
     *  3. keyGenerator: key的生成器; 可以自己指定key的生成器的组件id
     *          key/keyGenerator 二选一
     *
     *  4. cacheManager： 指定缓存管理器
     *  5. cacheResolver: 指定缓存解析器
     *          cacheManager/cacheResolver 二选一
     *
     *  6. condition: 指定符合条件的情况下才缓存
     *          condition = "#id>1"  condition = "#a0>1" 第一个参数id>1 保存
     *  7. unless: 当unless指定条件为true, 方法返回值不会被缓存，可以获取到结果进行半段
     *          unless = "#result==null"
     *  8. sync: 是否使用异步模式，不支持unless
     *
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp"
//            , keyGenerator = "myKeyGenerator"
            , condition = "#a0>1"
            , unless = "#a0==2")
    public Employee getEmpById(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut 既调用方法，又更新缓存数据; 同步缓存
     * 修改了数据库的某个数据，同时更新缓存
     * 运行时机:
     *  1. 先调用目标方法
     *  2. 将目标方法的结果缓存起来
     *
     * 测试步骤:
     *  1. 查询3号员工; 查到的结果会放在缓存中
     *      key:3 value:lastName:www
     *  2. 以后查询还是之前的结果
     *  3. 更新3号员工; lastName=hfoiefw gender=0
     *      将方法的返回值也放进缓存了
     *      key: 传入的employee对象 值：返回的employee对象
     *  4. 查询3号员工？
     *      应该是更新后的员工:
     *          key="#employee.id": 使用传入的参数的员工id
     *          key="#result.id": 使用返回后的id
     *              @Cachable 的key不能用result
     *      没有更新，因为3号员工没有在缓存中更新
     *
     */
    @CachePut(value = "emp", key="#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp: " + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }


    /**
     * @CacheEvict 缓存清除
     *  key: 指定要清除的数据
     *  allEntries=true: 删除该缓存中所有数据
     *  beforeInvocation=false : 缓存的清除是否在方法之前执行
     *      默认代表在方法执行之后执行; 如果出现异常缓存不会清除
     *  beforeInvocation=true: 之前执行，无论有无异常，都会清除
     *
     */
    @CacheEvict(value="emp", /*key="#id", */allEntries = true)
    public void deleteEmp(Integer id) {
        System.out.println("delete emp ...");

    }


    /**
     * @Caching 定义复杂的缓存规则
     *
     */
    @Caching(
            cacheable = {
                    @Cacheable(/*value="emp",*/ key = "#lastName")
            },
            put = {
                    @CachePut(/*value = "emp",*/ key = "#result.id"),
                    @CachePut(/*value = "emp",*/ key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }

}















