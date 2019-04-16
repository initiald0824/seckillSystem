package com.initiald.seckill.dao;

import com.initiald.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author initiald0824
 * @date 2019/4/15 21:26
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);


    @Insert("insert into user(id, name) values (#{id}, #{name})")
    public int insert(User user);
}
