package com.wzl.service;

import com.wzl.bean.Department;
import com.wzl.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by thinkpad on 2018/10/2.
 */

@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    RedisCacheManager cacheManager;

//    @Cacheable(cacheNames = "dept")
//    public Department getDeptById(Integer id) {
//        System.out.println("查询部门" + id);
//        Department department = departmentMapper.getDeptById(id);
//        return department;
//    }


    //使用缓存管理器得到缓存，进行api调用
    //先注入RedisCacheManager
    public Department getDeptById(Integer id) {
        System.out.println("查询部门" + id);
        Department department = departmentMapper.getDeptById(id);

        //获取某个缓存
        Cache dept = cacheManager.getCache("dept");
        dept.put("dept:keyyy", department);

        return department;
    }


}
