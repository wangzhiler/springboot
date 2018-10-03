package com.wzl.mapper;

import com.wzl.bean.Employee;

/**
 * Created by thinkpad on 2018/10/1.
 */

//@mapper或者@MapperScan将接口扫描装配到容器中
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
