package com.sohu.modules.redpacket.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;


//@DisallowConcurrentExecution
public class UpdateAccountTask extends QuartzJobBean{



	String accountSql ="MERGE INTO sw_account_info A\r\n" +
			"USING (select merchant_id, user_name, sum(amt) rp_amt,sum(decode(r_type,0,0,1,amt,0)) rw_amt\r\n" +
			"         from SW_REDPACKET_RECEIVE_INFO\r\n" +
			"        where  BATCH_ID in (replace_param_tag)  \r\n" +
			"        group by merchant_id, user_name) B\r\n" +
			"ON (A.merchant_id = B.merchant_id and A.user_name = B.user_name)\r\n" +
			"WHEN MATCHED THEN\r\n" +
			"  UPDATE SET A.account_amt = A.account_amt + B.rp_amt,A.WITHDRAW_AMT = A.WITHDRAW_AMT + B.rw_amt";


	String recieveSql = "MERGE INTO SW_REDPACKET_INFO A\r\n" +
			"USING (select FR_ID, sum(amt) rp_amt\r\n" +
			"         from SW_REDPACKET_RECEIVE_INFO\r\n" +
			"        where   BATCH_ID in (replace_param_tag)  \r\n" +
			"        group by FR_ID) B\r\n" +
			"ON (A.PS_TRANS_ID = B.FR_ID)\r\n" +
			"WHEN MATCHED THEN\r\n" +
			"  UPDATE SET A.remainder_amt = A.remainder_amt - B.rp_amt";

	


	@Autowired
	private JdbcTemplate jdbcTemplate;

	private long id ;
	private Long [] ids = new Long[2];

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
//		System.out.println("初始id:"+id);
//
//		do {
//			updateAccount();
//		}while(true);


	}
	
	
	@Transactional
	private void updateAccount() {

		List<Long> batchId = getRedBatchId();
		System.out.println("batchId  size:"+batchId.size());
		
		if(batchId == null || batchId.size() == 0) {
			try {
				
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return ; //如果查询批次 没有数据则 线程休眠1s然后再跳出方法 循环进入方法。
	    }
		//拼装sql
		String tAccountSql = getUpdateSqlOfInTag(accountSql  , batchId.size());
		String trecieveSql = getUpdateSqlOfInTag(recieveSql  , batchId.size());

		
		jdbcTemplate.update(tAccountSql,batchId.toArray());
		jdbcTemplate.update(trecieveSql,batchId.toArray());
		
		// 执行完 红包表和账户表的更新后， 去更新批次的状态信息。 
		/**组装update语句**/
        String updateSql = "update SW_REDPACKET_RECEIVE_BATCH set sum_status = 0, update_on = sysdate where batch_id in(-1";
        for(Long bId : batchId) {
            updateSql += ", ?";
        }
        updateSql += ")";
        System.out.println(updateSql);
        
        jdbcTemplate.update(updateSql, batchId.toArray());
	}
    
	
	/**
     *	获取最近24小时的未累计的前500个领取批次号
     * @return
     */
    private List<Long> getRedBatchId() {

        List<Long> batchList = new ArrayList<>();

        String querySql = "select batch_id from SW_REDPACKET_RECEIVE_BATCH t where created_on > sysdate-1 and sum_status = 1 and rownum < 500";

        List<Map<String , Object>> result = jdbcTemplate.queryForList(querySql,new Object[] {});

        if(result != null) {
            for(Map<String , Object> bchMap : result) {
                batchList.add(((BigDecimal) bchMap.get("BATCH_ID")).longValue());
            }
        }

        return batchList;
    }
    
    
    /**
     *	 根据参数个数拼装in标签
     * @param megreSql  带有replace_param_tag字符串的原始sql
     * @param paramNum  参数个数，根据参数个数拼装"?"
     * @return 拼装好的sql
     */
    private String getUpdateSqlOfInTag(String megreSql, Integer paramNum) {

        String inParam = "-1";
        for(int i=0; i<paramNum; i++) {
            inParam += ", ?";
        }
        System.out.println(inParam);
        return megreSql.replace("replace_param_tag", inParam);
    }
    
    
	
	 /**
     * 	更新红包批次累计状态
     * @param batchId
     * @return
     */
//    private int updateRedBatch(List<Long> batchId) {
//
//        if(batchId == null || batchId.size() == 0) {
//            return 0;
//        }
//        /**组装update语句**/
//        String updateSql = "update SW_REDPACKET_RECEIVE_BATCH set sum_status = 0, update_on = sysdate where batch_id in(-1";
//        for(Long bId : batchId) {
//            updateSql += ", ?";
//        }
//        updateSql += ")";
//
//        return jdbcTemplate.update(updateSql, batchId.toArray());
//    }

}
