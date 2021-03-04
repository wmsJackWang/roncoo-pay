package com.sohu.modules.redpacket.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sohu.common.asynchronous.IQueueJob;
import com.sohu.modules.redpacket.dao.LoadDatabaseDao;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
@Service
@Scope("prototype")
public	class RedpacketQueueJob implements IQueueJob<RedpacketReceiveInfoEntity> {
		private Logger log = LogManager.getLogger(this.getClass());
		private List<RedpacketReceiveInfoEntity> receiveEntittList;
		private String listKeyName;
//		
		@Resource(name="redpacketLoadDataBase")
		private LoadDatabaseDao<RedpacketReceiveInfoEntity> loadDatabaseDao;
		
		public RedpacketQueueJob(){
//			loadDatabaseDao = (RedpacketLoadDataBase) SpringUtil.getBean("redpacketLoadDataBase");
		}
		
		//每个批次都对应一个  备份 队列  ，更新 发红包bean 对象 数据  ，  插入  领取红包请求对象  ， 账户金额/可提现金额  更新。
		//所有流程 执行完成后  删除 备份队列
		@Override
		public void run() {
//			System.out.println(listKeyName+"进入任务，任务执行开始。。。。");

//			System.out.println(listKeyName+"进入任务，任务执行开始。。。。 任务中  数据长度"+receiveEntittList.size());
			log.info("RedpacketQueueJob执行randomKye:【"+listKeyName+"】"+receiveEntittList);
			Long save_start = System.currentTimeMillis();
			loadDatabaseDao.save(listKeyName, receiveEntittList);
			Long save_stop = System.currentTimeMillis();
			System.out.println("save method============================="+(save_stop-save_start));
		}
		

		@Override
		public void setQueueName(String queueName) {
			this.listKeyName = queueName;
		}

		@Override
		public void setValues(List<RedpacketReceiveInfoEntity> queueName) {
			receiveEntittList = new ArrayList<>();
			receiveEntittList.addAll(queueName);
		}
}