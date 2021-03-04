package com.sohu.common.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sohu.config.StatsdConfig;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

@Component
public class UtilStatsd implements ApplicationListener<EmbeddedServletContainerInitializedEvent>{
	private static StatsDClient statsdc;
	private static UtilStatsd singleton;

	private final static Object CLOCK = new Object();

	private static StatsdConfig cofnig;
	
	private String projectName;
	private int serverPort; 
	@Autowired
	private UtilStatsd(StatsdConfig cofnig) {
			this.cofnig = cofnig;
	}
	public static void recordExecutionTime(String aspect, int timeInMs) {
		statsdc.recordExecutionTime(aspect+"."+cofnig.getHostIp(), timeInMs);
	}

	public static void incrementCounter(String string) {
		statsdc.incrementCounter(string);
	}
	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		this.serverPort = event.getEmbeddedServletContainer().getPort();
		projectName = cofnig.getProjectName()+"."+IPUtils.getAddress()+"."+serverPort;
		statsdc = new NonBlockingStatsDClient(cofnig.getProjectName(), cofnig.getIp(), cofnig.getPort());
	}

}
