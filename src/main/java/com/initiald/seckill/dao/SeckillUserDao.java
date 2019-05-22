package com.initiald.seckill.dao;

import com.initiald.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author initiald0824
 * @date 2019/5/22 20:07
 */
@Mapper
public interface SeckillUserDao {

    @Select("select * from seckill_user where id = #{id}")
    public SeckillUser getById(@Param("id") long id);
}
