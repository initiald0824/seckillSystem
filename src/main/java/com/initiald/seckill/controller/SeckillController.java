package com.initiald.seckill.controller;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillOrder;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.GoodsService;
import com.initiald.seckill.service.OrderService;
import com.initiald.seckill.service.SeckillService;
import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.OrderDetail;
import com.initiald.seckill.vo.SeckillInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author initiald0824
 * @date 2019/6/6 22:34
 */

@RestController
@RequestMapping("/api")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/seckill", method = RequestMethod.POST)
    public Result seckill(SeckillUser user, @RequestParam("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsDetail(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0) {
            throw new GlobalException(CodeMsg.SECKILL_OVER);
        }

        // 判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            // 已经存在，重复秒杀
            throw new GlobalException(CodeMsg.SECKILL_REPEATE);
        }

        // 减库存， 下订单，写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(user, goods);

        SeckillInfo seckillInfo = new SeckillInfo();
        seckillInfo.setGoodsVo(goods);
        seckillInfo.setOrderInfo(orderInfo);
        return Result.success(seckillInfo);
    }

    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public Result getOrderDetail(SeckillUser user, @RequestParam("orderId") long orderId) {
        return Result.success(seckillService.getOrderDetail(user, orderId));
    }
}
