package com.wzl.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by thinkpad on 2018/10/3.
 */

@Service
public class ScheduledService {


    /**
     * second 0-59  ,*-/
     * minute 0-59  ,*-/
     * hour   0-23  ,*-/
     * day of month     1-31 ,*-?/LWC
     * month and day of week    1-12  ,*-/
     * 0 * * * * MON-FRI    0-7 SUN-SAT 0,7是SUN  ,*-?/LC#
     *
     * 0-4 区间
     * 0/4 从0开始每4秒一次
     * * 任意
     * ? 日/星期冲突匹配
     * L 最后
     * W 工作日
     * # 星期 eg 4#2 第2个星期四
     *
     * 【0 0/5 14,18 * * ?】 每天14点整，18点整，每隔5分钟执行一次
     * 【0 15 10 ? * 1-6】 每个月的周一至周六10:15分执行一次
     * 【0 0 2 ? * 6L】 每个月的最后一个周六凌晨2点执行一次
     * 【0 0 2 LW * ?】 每个月的最后一个工作日凌晨2点执行一次
     * 【0 0 2-4 ? * 1#1】 每个月的第一个周一凌晨2点到4点期间，每个整点都执行一次
     *
     */
//    @Scheduled(cron = "0 * * * * MON-SAT")
    @Scheduled(cron = "0,1,2,3,4,20 * * * * MON-SAT") //枚举
    public void hello() {
        System.out.println("helloooo");
    }
}
