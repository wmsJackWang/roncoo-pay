package com.sohu.common.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class BlockingQueueThreadPoolConfig {
	@Value("${block.queue.corepoolsize}")
	private String corepoolsize;
	@Value("${block.queue.maxpoolsize}")
	private String maxpoolsize;
	@Value("${block.queue.allow}")
	private String allow;
	@Value("${block.queue.keepalivetime}")
	private String keepalivetime;
	@Value("${block.queue.queuecapacity}")
	private String queuecapacity;
	public String getCorepoolsize() {
		return corepoolsize;
	}
	public void setCorepoolsize(String corepoolsize) {
		this.corepoolsize = corepoolsize;
	}
	public String getMaxpoolsize() {
		return maxpoolsize;
	}
	public void setMaxpoolsize(String maxpoolsize) {
		this.maxpoolsize = maxpoolsize;
	}
	public String getAllow() {
		return allow;
	}
	public void setAllow(String allow) {
		this.allow = allow;
	}
	public String getKeepalivetime() {
		return keepalivetime;
	}
	public void setKeepalivetime(String keepalivetime) {
		this.keepalivetime = keepalivetime;
	}
	public String getQueuecapacity() {
		return queuecapacity;
	}
	public void setQueuecapacity(String queuecapacity) {
		this.queuecapacity = queuecapacity;
	}
	
	
}
