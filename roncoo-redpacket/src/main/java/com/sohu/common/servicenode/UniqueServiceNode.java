package com.sohu.common.servicenode;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sohu.modules.redpacket.service.IServiceNodeInfoService;

@Component
public class UniqueServiceNode implements ApplicationRunner{

	
	@Resource(name="ServiceNodeInfoServiceImpl")	
	private  IServiceNodeInfoService nodeInfoService;
	


	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("应用启动");
//		nodeInfoService.getNodeId();
		
	}
	

}
