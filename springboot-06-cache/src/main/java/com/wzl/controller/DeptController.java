package com.wzl.controller;

import com.wzl.bean.Department;
import com.wzl.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by thinkpad on 2018/10/2.
 */

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/{id}")
    public Department getDept(@PathVariable("id") Integer id) {
//        System.out.println("查询部门" + id);
        Department deptById = deptService.getDeptById(id);
        return deptById;
    }

}
