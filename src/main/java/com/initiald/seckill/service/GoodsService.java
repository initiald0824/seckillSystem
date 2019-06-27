package com.initiald.seckill.service;

import com.initiald.seckill.dao.GoodsDao;
import com.initiald.seckill.domain.Goods;
import com.initiald.seckill.domain.SeckillGoods;
import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.SeckillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author initiald0824
 * @date 2019/6/5 16:22
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public SeckillGoodsVo getGoodsDetail(long id) {
        SeckillGoodsVo seckillGoodsVo = goodsDao.getById(id);
        seckillGoodsVo.setSeckillStatus();
        return seckillGoodsVo;
    }

    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(seckillGoods);
        return ret > 0;
    }
}
