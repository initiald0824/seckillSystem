package com.initiald.seckill.dao;

import com.initiald.seckill.domain.Goods;
import com.initiald.seckill.domain.SeckillGoods;
import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.SeckillGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author initiald0824
 * @date 2019/6/5 16:24
 */

@Mapper
public interface GoodsDao {

    @Select("select g.*, sg.stock_count, sg.seckill_price, sg.start_date, sg.end_date from seckill_goods sg left join goods g on sg.goods_id=g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, sg.stock_count, sg.seckill_price, sg.start_date, sg.end_date " +
            "from seckill_goods sg left join goods g on sg.goods_id=g.id where g.id=#{goodsId}")
    public SeckillGoodsVo getById(@Param("goodsId") Long id);

    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    public int reduceStock(SeckillGoods seckillGoods);
}
