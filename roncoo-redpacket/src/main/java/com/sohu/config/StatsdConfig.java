package com.sohu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sohu.common.utils.IPUtils;
@Component
public class StatsdConfig {
	@Value("${stats.ip}")
	private String ip;
	@Value("${stats.port}")
	private int port;
	@Value("${stats.projectName}")
	private String projectName;
	@Value("${stats.host.ip:127-0-0-1}")
	private String hostIp;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getHostIp() {
		if("127-0-0-1".equals(hostIp)) {
			hostIp = IPUtils.getAddress().getHostAddress();
			if(!StringUtils.isEmpty(hostIp)) {
				hostIp = hostIp.replaceAll("\\.", "-");
			}
		}
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		
		this.hostIp = hostIp;
	}
}
