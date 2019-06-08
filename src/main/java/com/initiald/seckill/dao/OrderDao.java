package com.initiald.seckill.dao;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * @author initiald0824
 * @date 2019/6/6 23:24
 */

@Mapper
public interface OrderDao {
    @Select("select * from seckill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info (user_id, goods_id, goods_name, goods_count, goods_price, " +
            "order_channel, status, create_date) values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}," +
            "#{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false,
            statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into seckill_order (user_id, goods_id, order_id) values(#{userId}, #{goodsId}, #{orderId})")
    public int insertSeckillOrder(SeckillOrder seckillOrder);
}
