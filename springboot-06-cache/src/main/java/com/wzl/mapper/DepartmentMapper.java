package com.wzl.mapper;

import com.wzl.bean.Department;
import org.apache.ibatis.annotations.Select;

/**
 * Created by thinkpad on 2018/10/1.
 */

public interface DepartmentMapper {
    @Select("SELECT * FROM department WHERE id=#{id}")
    Department getDeptById(Integer id);
}
