package com.sohu.modules.redpacket.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohu.common.annotation.MethodLog;
import com.sohu.common.validator.Assert;
import com.sohu.modules.account.entity.AccountInfoEntity;
import com.sohu.modules.redpacket.dao.LoadDatabaseDao;
import com.sohu.modules.redpacket.entity.RedpacketInfoEntity;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
import com.sohu.modules.redpacket.service.AbsRedpacket;
//@Service
public class RedpacketLoadDataBase implements LoadDatabaseDao<RedpacketReceiveInfoEntity>{
//	private Logger log = LogManager.getLogger(this.getClass());
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//	
//	private static Object lock = new Object();
//	
//
//	
	
	@Transactional
	@MethodLog
	//备份队列名 listKeyName   , 工作队列拉取的批量数据receiveListMap
	public void save(String listKeyName,List<RedpacketReceiveInfoEntity> receiveListMap) {
//		ArrayList<Object[]> paraInsertReceiveInfo = new ArrayList<>();
//		try{
//			
//			long batch_Id = initOtherInfo(paraInsertReceiveInfo, receiveListMap);
//			Long db_start = System.currentTimeMillis();
//			String receiveInfoInsert = "INSERT /*+ append */ into SW_REDPACKET_RECEIVE_INFO (ID,VERSION_OPTIMIZED_LOCK,CREATED_ON,UPDATED_ON,MERCHANT_ID,PRODUCT_ID,PSID,FR_ID,PS_TRANS_ID,USER_NAME,R_TYPE,AMT,CALLBACK_URL,PLAN_AMT,BATCH_ID) values(SEQ_SW_REDPACKET_RECEIVE_INFO.NEXTVAL ,1,?,sysdate,?,?,?,?,?,?,?,?,?,?,?)";
//			jdbcTemplate.batchUpdate(receiveInfoInsert, paraInsertReceiveInfo);
//			Long db_stop = System.currentTimeMillis();
//			insertBatchId(batch_Id);
//			System.out.println("db============================="+(db_stop-db_start));
//			//存库后删除key，都执行成功后删除   领取红包请求 数据的备份队列。
//			redisTemplate.delete(listKeyName);
//			
//			
//		}catch (Exception e) {
//			log.error("RedpacketQueueJob执行randomKye:【"+listKeyName+"】失败"+receiveListMap,e);
//
//			
//		}
	}

    /**
     * 	从序列中获取红包领取批次ID
     * @return
     */
//    private Long getBatchIdFromSeq() {
//
//        String querySql = "select SEQ_SW_REDPACKET_RECEIVE_BATCH.NEXTVAL as batch_id from dual";
//
//        Long batchId = ((BigDecimal)jdbcTemplate.queryForList(querySql,new Object[] {}).get(0).get("BATCH_ID")).longValue();
//
//        return batchId;
//    }
//
//
//
//    /**
//     *	 插入红包领取批次表
//     * @param batchId
//     * @return
//     */
//    private int insertBatchId(Long batchId) {
//
//        String insertSql = "insert into SW_REDPACKET_RECEIVE_BATCH (batch_id, sum_status) values(?, 1)";
//
//        return jdbcTemplate.update(insertSql, batchId);
//    }
//	
//
//	/**
//	 * @Desc   : <P>生成更新用户余额sql参数和插入领取红包sql参数<P>
//	 * @author : SOHU-wanghonghui
//	 * @date   : 2017年9月28日 下午5:31:51
//	 * @Version: V1.0
//	 * @param paraUpdateAccount
//	 * @param paraInsertReceiveInfo void
//	 */
//	protected long initOtherInfo(ArrayList<Object[]> paraInsertReceiveInfo,List<RedpacketReceiveInfoEntity> receiveListMap) {
//		
//		long batch_Id = getBatchIdFromSeq();
//		System.out.println("批次号:"+batch_Id);
//		for (int i = 0; i < receiveListMap.size(); i++) {
//			RedpacketReceiveInfoEntity receiveInfoEntity = receiveListMap.get(i);
//
//			paraInsertReceiveInfo.add(new Object[]{receiveInfoEntity.getCreatedOn(),receiveInfoEntity.getMerchantId(),receiveInfoEntity.getProductId(),receiveInfoEntity.getPsid(),receiveInfoEntity.getFrId(),receiveInfoEntity.getPsTransId(),receiveInfoEntity.getUserName(),receiveInfoEntity.getrType(),receiveInfoEntity.getAmt(),receiveInfoEntity.getCallbackUrl(),receiveInfoEntity.getPlanAmt(),batch_Id});
//		}
//		
//		return batch_Id;
//	}
//	/**
//	 * @Desc   : <P>生成更新红包信息sql参数,并更新用户可领取红包钱数<P>
//	 * @author : SOHU-wanghonghui
//	 * @param filterRedpacket 
//	 * @date   : 2017年9月28日 下午5:23:43
//	 * @Version: V1.0
//	 * @param buf
//	 * @return ArrayList<Object[]>
//	 */
//	protected ArrayList<RedpacketReceiveInfoEntity> initParaUpdateRedPacket(TreeSet<String> filterRedpacket, ArrayList<Object[]> paraUpdateRedPacket,List<RedpacketInfoEntity> queryForList,List<RedpacketReceiveInfoEntity> receiveListMap) {
//		//存放收红包 请求数据 ，不仅仅是普通的数据，而是无法获取红包的数据。
//		ArrayList<RedpacketReceiveInfoEntity> warrInfo = new ArrayList<>();
//		
//		/*
//		 *  红包 领取逻辑：
//		 *  1. 发红包订单集合queryForList 是根据  领取红包请求集合 receiveListMap 中所有红包id 查出来的结果。
//		 *  2. 遍历每一个发红包订单 ， 在循环体内再遍历所有 领取红包请求集合 ，一个一个对比
//		 *  3. 如果 领取红包请求 中红包id 和 发红包订单  id一样 ，则进行红包领取核心逻辑
//		 *  4. 依次对每个红包订单  进行领取操作。(每个订单数据  对领取请求集合  做循环遍历  减金额操作 。)
//		 *  5. 该批次 的 发红包订单 状态  和  领取红包的订单状态  如下：
//		 *  		发红包订单： 金额为0 ，订单状态变成关闭-end; 订单金额大于0 
//		 *  		领取红包状态：  领取成功 ，领取失败。
//		 *  6. 生成所有 发红包订单的  update  sql 集合。
//		 */
//		for (String redpacketId : filterRedpacket) {
////			redpacketInfoEntity.setAmt(BigDecimal.ZERO);
//			BigDecimal _remainderAmt = BigDecimal.ZERO;
//			String frId = redpacketId;
//			for (RedpacketReceiveInfoEntity receiveInfoEntity : receiveListMap) {
//				if(frId.equals(receiveInfoEntity.getFrId())){
//					_remainderAmt = _remainderAmt.add(receiveInfoEntity.getAmt());
//					
////					_remainderAmt=_remainderAmt.subtract(receiveInfoEntity.getAmt());
////					//如果  发红包 订单  金额变成0 则订单状态变成结束  end
////					if(_remainderAmt.compareTo(BigDecimal.ZERO)==0) {
////						redpacketInfoEntity.setStatus(Integer.valueOf(RedpacketInfoEnum.STATUS_END.get()));
//////						receiveInfoEntity.setAmt(BigDecimal.ZERO);
////						
////						
////						//如果订单金额变成了小于0 的数 , 则该 领取红包的 操作将失败 ，红包订单金额恢复 ， 领取红包请求对象 加入  领取失败集合中 去 ， 并设置领取金额为0。
////					}else if(_remainderAmt.compareTo(BigDecimal.ZERO)<0){
////						_remainderAmt =_remainderAmt.add(receiveInfoEntity.getAmt());
//////						redpacketInfoEntity.setStatus(Integer.valueOf(RedpacketInfoEnum.STATUS_END.get()));
////						warrInfo.add(receiveInfoEntity);
////						receiveInfoEntity.setAmt(BigDecimal.ZERO);
////					}
//				}
//			}
//			//更新发红包订单   的金额和状态数据  ， 从库中查询出来的红包都会 对这个批次  领取红包的数据 集合   处理完过后   生成最终的更新sql  。
//			paraUpdateRedPacket.add(new Object[]{_remainderAmt,0,redpacketId,_remainderAmt});
//		}
//		return warrInfo;
//	}
//	/**
//	 * @Desc   : <P>初始锁定红包语句sql,锁定这些数据库中的发红包表订单数据<P>
//	 * @author : SOHU-wanghonghui
//	 * @date   : 2017年9月28日 下午5:14:08
//	 * @Version: V1.0
//	 * @return String
//	 */
//	protected String initRedpacketSelectSql(TreeSet<String> set,List<RedpacketReceiveInfoEntity> receiveListMap) {
//		
//		StringBuffer buf = new StringBuffer("SELECT PS_TRANS_ID,REMAINDER_AMT,STATUS  FROM SW_REDPACKET_INFO WHERE PS_TRANS_ID IN  (");
//		for (String psTransId : set) {
//			buf.append("'");
//			buf.append(psTransId);
//			if(!set.last().equals(psTransId)){
//				buf.append("',");
//			}else{
//				buf.append("'");
//			}
//		}
//		buf.append(") ");
//		return buf.toString();
//	}
}
