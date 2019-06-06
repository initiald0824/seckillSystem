package com.initiald.seckill.dao;

import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.SeckillGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    public SeckillGoodsVo getById(@Param("goodsId") Integer id);
}
