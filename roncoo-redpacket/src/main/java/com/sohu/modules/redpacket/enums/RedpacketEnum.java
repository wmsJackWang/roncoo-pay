package com.sohu.modules.redpacket.enums;


import com.sohu.common.baseenum.BaseEnum.CheckParameterExceptionEnum;
import com.sohu.common.validator.Assert;

public class RedpacketEnum {

	public enum RedpacketCheckBusinessExcption{
		//领取红包订单重复
		REDPACKET_CHECK_TRANS_REPEAT(2,"红包领取成功");
		private int code;
		private String msg;
		
		RedpacketCheckBusinessExcption(int code,String msg){
			this.code = code;
			this.msg = msg;
		}
		
		public int getCode(){
			return code;
		}

		public String getMsg() {
			return msg;
		}

	}
	public enum RedpacketExcption{
		//红包管理队列超长警告
		REDPACKET_QUEUE_BAK_MANAGE_WARN_OVERLENGTH("REDPACKET_QUEUE_BAK_MANAGE_WARN_OVERLENGTH:"),
		//红包失败管理长度警告
		REDPACKET_QUEUE_BAK_MANAGE_FAIL_OVERLENGTH("REDPACKET_QUEUE_BAK_MANAGE_EXCPTION_OVERLENGTH:"),
		//用户领取红包金额大于总金额用户,不更新用户余额警告
		REDPACKET_USER_RECEIVE_GREATER_TOTAL_AMT("REDPACKET_USER_RECEIVE_GREATER_TOTAL_AMT:");
		private String msg;
		
		RedpacketExcption(String msg){
			this.msg = msg;
		}
		
		public String get(){
			return msg;
		}
	}
	
	public enum RedisQueueName{
		REDPACKET("REDPACKET"),
		//空字符串
		NULL_STRING(""),
		UNDERLINE_STRING("_"),
		//备份队列前缀名称
		REDPACKET_QUEUE_BAK_PREFIX("REDPACKET_QUEUE_BAK_"),
		//备份队列key管理队列
		REDPACKET_QUEUE_BAK_MANAGE_KEY("REDPACKET_QUEUE_BAK_MANAGE_KEY"),
		//封装随机备份队列key值MAP常量
		REDPACKET_MAP_QUEUE_BAK_KEY_FINAL("REDPACKET_MAP_QUEUE_BAK_KEY_FINAL"),
		//封装随机备份队列状态值MAP常量
		REDPACKET_MAP_QUEUE_BAK_STATE_FINAL("REDPACKET_MAP_QUEUE_BAK_STATE_FINAL"),

		//一下为存储红包的队列
		REDPACKET_QUEUE_1("REDPACKET_QUEUE_1"),
		REDPACKET_QUEUE_2("REDPACKET_QUEUE_2"),
		REDPACKET_QUEUE_3("REDPACKET_QUEUE_3"),
		REDPACKET_QUEUE_4("REDPACKET_QUEUE_4"),
		REDPACKET_QUEUE_5("REDPACKET_QUEUE_5"),
		REDPACKET_QUEUE_6("REDPACKET_QUEUE_6"),
		REDPACKET_QUEUE_7("REDPACKET_QUEUE_7"),
		REDPACKET_QUEUE_8("REDPACKET_QUEUE_8");
		private String msg;
		
		RedisQueueName(String msg){
			this.msg = msg;
		}
		
		public String get(){
			return msg;
		}
		
		
		
		
		
		public static String getWorkQueue(int index){
			String queueName = "";
			switch (index) {
				case 1 :
					queueName = REDPACKET_QUEUE_1.get();
					break;
				case 2 :
					queueName = REDPACKET_QUEUE_2.get();
					break;
				case 3 :
					queueName = REDPACKET_QUEUE_3.get();
					break;
				case 4 :
					queueName = REDPACKET_QUEUE_4.get();
					break;
				case 5 :
					queueName = REDPACKET_QUEUE_5.get();
					break;
				case 6 :
					queueName = REDPACKET_QUEUE_6.get();
					break;
				case 7 :
					queueName = REDPACKET_QUEUE_7.get();
					break;
				case 8 :
					queueName = REDPACKET_QUEUE_8.get();
					break;
				default :
					Assert.isTrue(true,CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),"红包队列不存在");
			}
			return queueName;
		}
	}
	
	public enum RedisAccountQueueName{
		ACCOUNT("ACCOUNT"),
		//空字符串
		NULL_STRING(""),
		UNDERLINE_STRING("_"),
		//备份队列前缀名称
		ACCOUNT_QUEUE_BAK_PREFIX("ACCOUNT_QUEUE_BAK_"),
		//备份队列key管理队列
		ACCOUNT_QUEUE_BAK_MANAGE_KEY("ACCOUNT_QUEUE_BAK_MANAGE_KEY"),
		//封装随机备份队列key值MAP常量
		ACCOUNT_MAP_QUEUE_BAK_KEY_FINAL("ACCOUNT_MAP_QUEUE_BAK_KEY_FINAL"),
		//封装随机备份队列状态值MAP常量
		ACCOUNT_MAP_QUEUE_BAK_STATE_FINAL("ACCOUNT_MAP_QUEUE_BAK_STATE_FINAL"),

		//一下为存储红包的队列
		ACCOUNT_QUEUE_1("ACCOUNT_QUEUE_1"),
		ACCOUNT_QUEUE_2("ACCOUNT_QUEUE_2"),
		ACCOUNT_QUEUE_3("ACCOUNT_QUEUE_3"),
		ACCOUNT_QUEUE_4("ACCOUNT_QUEUE_4"),
		ACCOUNT_QUEUE_5("ACCOUNT_QUEUE_5"),
		ACCOUNT_QUEUE_6("ACCOUNT_QUEUE_6"),
		ACCOUNT_QUEUE_7("ACCOUNT_QUEUE_7"),
		ACCOUNT_QUEUE_8("ACCOUNT_QUEUE_8");
		private String msg;
		
		RedisAccountQueueName(String msg){
			this.msg = msg;
		}
		
		public String get(){
			return msg;
		}
		
		
		
		
		
		public static String getWorkQueue(int index){
			String queueName = "";
			switch (index) {
				case 1 :
					queueName = ACCOUNT_QUEUE_1.get();
					break;
				case 2 :
					queueName = ACCOUNT_QUEUE_2.get();
					break;
				case 3 :
					queueName = ACCOUNT_QUEUE_3.get();
					break;
				case 4 :
					queueName = ACCOUNT_QUEUE_4.get();
					break;
				case 5 :
					queueName = ACCOUNT_QUEUE_5.get();
					break;
				case 6 :
					queueName = ACCOUNT_QUEUE_6.get();
					break;
				case 7 :
					queueName = ACCOUNT_QUEUE_7.get();
					break;
				case 8 :
					queueName = ACCOUNT_QUEUE_8.get();
					break;
				default :
					Assert.isTrue(true,CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),"账户队列不存在");
			}
			return queueName;
		}
	}
}
