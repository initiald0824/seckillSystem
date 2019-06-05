package com.initiald.seckill.service;

import com.initiald.seckill.dao.GoodsDao;
import com.initiald.seckill.vo.GoodsVo;
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
}
