package com.sohu.modules.redpacket.service;

import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
import com.sohu.modules.redpacket.enums.RedpacketEnum.RedisAccountQueueName;
import com.sohu.modules.redpacket.enums.RedpacketEnum.RedisQueueName;



public class AbsRedpacket {
	
	/**
	 * @Desc   : <P>获取红包唯一ID<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午4:37:20
	 * @Version: V1.0
	 * @param redpacketReceiveInfo
	 * @return String   REDPACKET_ProductId_FrId
	 */
	public static String getRedpacketOnlyKey(RedpacketReceiveInfoEntity redpacketReceiveInfo) {
		String redPacketKey = RedisQueueName.REDPACKET.get() + RedisQueueName.UNDERLINE_STRING.get()+redpacketReceiveInfo.getProductId()+RedisQueueName.UNDERLINE_STRING.get()+redpacketReceiveInfo.getFrId();
//		String redPacketKey = RedisQueueName.REDPACKET.get() + redpacketReceiveInfo.getProductId()+redpacketReceiveInfo.getFrId();
//		System.out.println(redPacketKey);
		return redPacketKey;
	}
	/**
	 * @Desc   : <P>生成红包领取唯一id<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午4:28:01
	 * @Version: V1.0
	 * @param frId
	 * @param psTransId
	 * @return String
	 */
	protected String getReceiveOnlyKey(Integer productId,String frId,String psTransId){
		return RedisQueueName.REDPACKET.get()+RedisQueueName.UNDERLINE_STRING.get()+ productId+RedisQueueName.UNDERLINE_STRING.get()+ frId+RedisQueueName.UNDERLINE_STRING.get()+psTransId;
	}
	
	/**
	 * @Desc   : <P>获取红包队列名称<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午4:37:40
	 * @Version: V1.0
	 * @param frId
	 * @return String
	 */
	protected String getQueueKey(String frId){
		int hashCode = frId.hashCode()& Integer.MAX_VALUE;
		int splitQueue = hashCode%8+1;
		return RedisQueueName.getWorkQueue(splitQueue);
	}
	
	/**
	 * @Desc   : <P>获取账户队列名称<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午4:37:40
	 * @Version: V1.0
	 * @param frId
	 * @return String
	 */
	protected String getAccountKey(String userName , Long merchantId){
		
		String hashId = merchantId + ":" + userName ;
		
		int hashCode = hashId.hashCode()& Integer.MAX_VALUE;
		int splitQueue = hashCode%8+1;
		return RedisAccountQueueName.getWorkQueue(splitQueue);
	}
	
	
	
	
	/**
	 * @Desc   : <P>获取用户信息在redis内唯一key<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午4:44:17
	 * @Version: V1.0
	 * @param redpacketReceiveInfo
	 * @return String
	 */
	protected String getUserInfoOnlyKey(RedpacketReceiveInfoEntity redpacketReceiveInfo) {
		String userKey = redpacketReceiveInfo.getMerchantId() + RedisQueueName.UNDERLINE_STRING.get() + redpacketReceiveInfo.getUserName();
		return userKey;
	}
	protected String getMerchantInfoKey(RedpacketReceiveInfoEntity redpacketReceiveInfo) {
		String userKey = "MP"+redpacketReceiveInfo.getMerchantId()  + redpacketReceiveInfo.getProductId();
		return userKey;
	}

}
