package com.sohu.common.asynchronous;

import java.util.List;

public interface IQueueJob<T> extends Runnable{

	void setQueueName(String queueName);
	void setValues(List<T> queueName);

}
