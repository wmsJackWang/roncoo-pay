package com.sohu.modules.redpacket.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sohu.common.asynchronous.AbsAsynchronousQueue;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
@Service
@Scope("prototype")
public class AsynchronousRedpackerQueueextends extends AbsAsynchronousQueue<RedpacketReceiveInfoEntity,RedpacketQueueJob>{

	public AsynchronousRedpackerQueueextends(){
		super();
	}

}