package com.wzl.repository;

import com.wzl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by thinkpad on 2018/10/1.
 */

//继承JpaRepository来完成对数据库的操作
//Repository<T, Id extends Serializablt> :同一接口
//RevisionRepository<T, ID extends Serializable, N extends Number&Comparable<N>> :基于乐观锁
//CrudRepository<T,ID extends Serializable>: 基本CRUD操作
//PagingAndSortingRepository<T,ID extends Serializable>: 基本CRUD及分页

public interface UserRepository extends JpaRepository<User, Integer> {
}
