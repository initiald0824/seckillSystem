package com.initiald.seckill.controller;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillOrder;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.rabbitmq.MqSender;
import com.initiald.seckill.rabbitmq.SeckillMessage;
import com.initiald.seckill.redis.AccessKey;
import com.initiald.seckill.redis.GoodsKey;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.redis.SeckillKey;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.GoodsService;
import com.initiald.seckill.service.OrderService;
import com.initiald.seckill.service.SeckillService;
import com.initiald.seckill.uitl.MD5Util;
import com.initiald.seckill.uitl.UUIDUtil;
import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.OrderDetail;
import com.initiald.seckill.vo.SeckillInfo;
import com.rabbitmq.client.AMQP;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author initiald0824
 * @date 2019/6/6 22:34
 */

@RestController
@RequestMapping("/api")
public class SeckillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MqSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList != null) {
            for (GoodsVo goodsVo: goodsList) {
                redisService.set(GoodsKey.getSeckillGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
                localOverMap.put(goodsVo.getId(), false);
            }
        }
    }

    @RequestMapping(value = "/seckill", method = RequestMethod.POST)
    public Result seckill(SeckillUser user, @RequestParam("seckillPath") String seckillPath,
                          @RequestParam("goodsId") long goodsId) {
        if (user == null || seckillPath == null) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }

        String seckillCode = redisService.get(SeckillKey.getSeckillPath, ""+user.getId()+"_"+goodsId, String.class);
        if (!seckillPath.equals(seckillCode)) {
            throw new GlobalException(CodeMsg.REQUEST_ILLEGAL);
        }

        boolean over = localOverMap.get(goodsId);
        if (over) {
            throw new GlobalException(CodeMsg.SECKILL_OVER);
        }

        // 预减库存
        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, ""+goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            throw new GlobalException(CodeMsg.SECKILL_OVER);
        }

        // 判断是否秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            // 已经存在，重复秒杀
            throw new GlobalException(CodeMsg.SECKILL_REPEATE);
        }

        // 入队
        SeckillMessage msg = new SeckillMessage();
        msg.setGoodsId(goodsId);
        msg.setUser(user);
        sender.sendSeckillMessage(msg);

        // 排队中
        return Result.success(0);

//        // 判断库存
//        int stock = goods.getGoodsStock();
//        if (stock <= 0) {
//            throw new GlobalException(CodeMsg.SECKILL_OVER);
//        }
//
//        // 判断是否已经秒杀到了
//        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (order != null) {
//            // 已经存在，重复秒杀
//            throw new GlobalException(CodeMsg.SECKILL_REPEATE);
//        }
//
//        // 减库存， 下订单，写入秒杀订单
//        OrderInfo orderInfo = seckillService.seckill(user, goods);
//
//        SeckillInfo seckillInfo = new SeckillInfo();
//        seckillInfo.setGoodsVo(goods);
//        seckillInfo.setOrderInfo(orderInfo);
//        return Result.success(seckillInfo);
    }

    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public Result getOrderDetail(SeckillUser user, @RequestParam("orderId") long orderId) {
        return Result.success(seckillService.getOrderDetail(user, orderId));
    }

    /**
     * orderId: 成功
     * -1: 失败
     * 0: 排队中
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/seckillStatus", method = RequestMethod.GET)
    public Result getSeckillResult(SeckillUser user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }

        long result = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @RequestMapping(value = "/seckillPath", method = RequestMethod.GET)
    public Result getSeckillPath(HttpServletRequest request, SeckillUser user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 查询访问次数
        String uri = request.getRequestURI();
        String key = uri + "_" + user.getId();
        Integer count = redisService.get(AccessKey.access, key, Integer.class);
        if (count == null) {
            redisService.set(AccessKey.access, key, 1);
        } else if(count < 5) {
            redisService.incr(AccessKey.access, key);
        } else {
            throw new GlobalException(CodeMsg.ACCESS_LIMIT);
        }
        String str = MD5Util.md5(UUIDUtil.uuid() + "seckill_address");
        redisService.set(SeckillKey.getSeckillPath, user.getId()+"_"+goodsId, str);
        return Result.success(str);
    }
}
