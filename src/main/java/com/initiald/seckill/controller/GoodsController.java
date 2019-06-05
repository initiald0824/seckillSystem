package com.initiald.seckill.controller;

import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.GoodsService;
import com.initiald.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author initiald0824
 * @date 2019/6/5 16:46
 */

@RestController
@RequestMapping("/api")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/list_goods")
    public Result listGoods() {
        return Result.success(goodsService.listGoodsVo());
    }
}
