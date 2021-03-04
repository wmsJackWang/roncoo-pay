package com.roncoo.pay.app.reconciliation.job;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zhenglongfei on 2019/7/24
 *
 * @VERSION 1.0
 */
@Component
@Slf4j
public class DynamicJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("TestJob任务名：【{}】, 片数：【{}】, 分片任务编号：【{}】, param=【{}】", shardingContext.getJobName(), shardingContext.getShardingTotalCount(),
        		shardingContext.getShardingItem(),shardingContext.getShardingParameter());
    }
}
