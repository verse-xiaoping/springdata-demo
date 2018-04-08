package com.github.springdata.repository;

import com.github.springdata.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by verse on 2018/4/8.
 */
public interface UserRepository  extends PagingAndSortingRepository<User, Integer> {
    /**
     * 通过姓名查找集合
     * @param userName
     * @return
     */
    List<User> findUserByUserName(String userName);

    /**
     * 模糊查找
     * @param userName
     * @return
     */
    @Query("from User where userName like %?1%")
    List<User> findByUserName(String userName);

    /**
     * 注解-修改
     */
    @Transactional
    @Modifying
    @Query("update User set userName = ?1 where id= ?2 ")
    void updateQuery(String userName, int id);
}
