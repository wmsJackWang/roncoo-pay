package com.sohu.common.baseenum;

/**
 * @Desc   : <P>续约常量<P>
 * @Author : SOHU-wanghonghui
 * @Date   : 2017年9月30日 下午2:02:12
 * @Version: V1.0
 */
public class RenewalEnum {

	
	public enum RenewallExceptionEnum{
		
		RENEWALL_EXCEPTION("checkException",9020,"参数错误"),
		SQL_FILTER_EXCEPTION("sqlFilterException",9001,"包含非法字符"),
		QUEUE_KEY_EXCEPTION("queueNotFindException",9900,"没有找到此队列");

		
		private String type;
		private int code;
		private String msg;
		
		RenewallExceptionEnum(String type,int code,String remark){
			this.type = type;
			this.code = code;
			this.msg = remark;
		}

		public String Type() {
			return type;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}
	
}
