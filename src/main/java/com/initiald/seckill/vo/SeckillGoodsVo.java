package com.initiald.seckill.vo;

import java.util.Date;

/**
 * @author initiald0824
 * @date 2019/6/6 15:29
 */
public class SeckillGoodsVo extends GoodsVo {

    private String seckillStatus;
    private Integer remainSeconds;

    public String getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus() {
        long startAt = super.getStartDate().getTime();
        long endAt = super.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if (now < startAt) {
            this.seckillStatus = "preparing";
            this.remainSeconds = (int)((startAt - now) / 1000);
        } else if (now > endAt) {
            this.seckillStatus = "ended";
            this.remainSeconds = -1;
        } else {
            this.seckillStatus = "started";
            this.remainSeconds = 0;
        }
    }

    public Integer getRemainSeconds() {
        return remainSeconds;
    }
}
