package com.initiald.seckill.vo;

import com.initiald.seckill.domain.Goods;

import java.util.Date;

/**
 * @author initiald0824
 * @date 2019/6/5 16:26
 */
public class GoodsVo extends Goods {

    private Integer stockCount;
    private Double seckillPrice;
    private Date startDate;
    private Date endDate;

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
