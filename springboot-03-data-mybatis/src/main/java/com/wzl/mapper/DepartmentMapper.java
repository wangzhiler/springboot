package com.wzl.mapper;

import com.wzl.bean.Department;
import org.apache.ibatis.annotations.*;

/**
 * Created by thinkpad on 2018/10/1.
 */

//指定这是一个操作数据库的mapper
//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    //自增
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(department_name) value(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}
