package com.sohu.modules.redpacket.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sohu.common.validator.group.AddGroup;
import com.sohu.common.validator.group.UpdateGroup;
import com.sohu.entity.base.ParameterEntity;



/**
 * 发红包记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
public class RedpacketInfoEntity extends ParameterEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//红包编号
	private Long id;
	
	
	//发红包账户名
	@NotNull(message = "用户名不能为空",groups = {AddGroup.class, UpdateGroup.class})
	@Size(min=1,max=64, message = "用户名长度只能为1-64位",groups = {AddGroup.class, UpdateGroup.class})
	private String userName;
	//红包剩余金额
	private BigDecimal remainderAmt;
	//预留字段1
	private String memo1;
	//PSID
	private Integer psid;
	//业务线流水号
	private String psTransId;
	//账户ID
	private Long accountId;
	//红包过期时间
	private Timestamp expireTime;
	//退款金额
	private BigDecimal refundAmt;
	//红包金额
	private BigDecimal amt;
	//创建时间
	private Timestamp createdOn;
	//产品id
	private Integer productId;
	//更新时间
	private Timestamp updatedOn;
	//按月分区
	private Integer partitionMonth;
	//预留字段4
	private String memo4;
	//乐观锁
	private Long versionOptimizedLock;
	//钱包流水号
	private String transId;
	//产品名称
	private String productName;
	//红包个数
	private Long total;
	//1:可提现红包;2:不可体现红包
	private Integer rType;
	//预留字段3
	private String memo3;
	//红包剩余个数
	private Long remainderTotal;
	//业务线ID
	private String merchantId;
	//预留字段2
	private String memo2;
	//红包状态0:正常可领取状态;1:领取完毕状态;2:过期退款状态
	private Integer status;

	/**
	 * 设置：红包编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：红包编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：发红包账户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：发红包账户名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：红包剩余金额
	 */
	public void setRemainderAmt(BigDecimal remainderAmt) {
		this.remainderAmt = remainderAmt;
	}
	/**
	 * 获取：红包剩余金额
	 */
	public BigDecimal getRemainderAmt() {
		return remainderAmt;
	}
	/**
	 * 设置：预留字段1
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	/**
	 * 获取：预留字段1
	 */
	public String getMemo1() {
		return memo1;
	}
	/**
	 * 设置：PSID
	 */
	public void setPsid(Integer psid) {
		this.psid = psid;
	}
	/**
	 * 获取：PSID
	 */
	public Integer getPsid() {
		return psid;
	}
	/**
	 * 设置：业务线流水号
	 */
	public void setPsTransId(String psTransId) {
		this.psTransId = psTransId;
	}
	/**
	 * 获取：业务线流水号
	 */
	public String getPsTransId() {
		return psTransId;
	}
	/**
	 * 设置：账户ID
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取：账户ID
	 */
	public Long getAccountId() {
		return accountId;
	}
	/**
	 * 设置：红包过期时间
	 */
	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：红包过期时间
	 */
	public Timestamp getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置：退款金额
	 */
	public void setRefundAmt(BigDecimal refundAmt) {
		this.refundAmt = refundAmt;
	}
	/**
	 * 获取：退款金额
	 */
	public BigDecimal getRefundAmt() {
		return refundAmt;
	}
	/**
	 * 设置：红包金额
	 */
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	/**
	 * 获取：红包金额
	 */
	public BigDecimal getAmt() {
		return amt;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * 获取：创建时间
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	/**
	 * 设置：产品id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品id
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	/**
	 * 获取：更新时间
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	/**
	 * 设置：按月分区
	 */
	public void setPartitionMonth(Integer partitionMonth) {
		this.partitionMonth = partitionMonth;
	}
	/**
	 * 获取：按月分区
	 */
	public Integer getPartitionMonth() {
		return partitionMonth;
	}
	/**
	 * 设置：预留字段4
	 */
	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}
	/**
	 * 获取：预留字段4
	 */
	public String getMemo4() {
		return memo4;
	}
	/**
	 * 设置：乐观锁
	 */
	public void setVersionOptimizedLock(Long versionOptimizedLock) {
		this.versionOptimizedLock = versionOptimizedLock;
	}
	/**
	 * 获取：乐观锁
	 */
	public Long getVersionOptimizedLock() {
		return versionOptimizedLock;
	}
	/**
	 * 设置：钱包流水号
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}
	/**
	 * 获取：钱包流水号
	 */
	public String getTransId() {
		return transId;
	}
	/**
	 * 设置：产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：红包个数
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * 获取：红包个数
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * 设置：1:可提现红包;2:不可体现红包
	 */
	public void setRType(Integer rType) {
		this.rType = rType;
	}
	/**
	 * 获取：1:可提现红包;2:不可体现红包
	 */
	public Integer getRType() {
		return rType;
	}
	/**
	 * 设置：预留字段3
	 */
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	/**
	 * 获取：预留字段3
	 */
	public String getMemo3() {
		return memo3;
	}
	/**
	 * 设置：红包剩余个数
	 */
	public void setRemainderTotal(Long remainderTotal) {
		this.remainderTotal = remainderTotal;
	}
	/**
	 * 获取：红包剩余个数
	 */
	public Long getRemainderTotal() {
		return remainderTotal;
	}
	/**
	 * 设置：业务线ID
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：业务线ID
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * 设置：预留字段2
	 */
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	/**
	 * 获取：预留字段2
	 */
	public String getMemo2() {
		return memo2;
	}
	/**
	 * 设置：红包状态0:正常可领取状态;1:领取完毕状态;2:过期退款状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：红包状态0:正常可领取状态;1:领取完毕状态;2:过期退款状态
	 */
	public Integer getStatus() {
		return status;
	}
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("com.sohu.entity.RedpacketInfoEntity [")
		.append(",remainderAmt=").append(remainderAmt)
		.append(",memo1=").append(memo1)
		.append(",psid=").append(psid)
		.append(",psTransId=").append(psTransId)
		.append(",accountId=").append(accountId)
		.append(",expireTime=").append(expireTime)
		.append(",refundAmt=").append(refundAmt)
		.append(",amt=").append(amt)
		.append(",createdOn=").append(createdOn)
		.append(",productId=").append(productId)
		.append(",updatedOn=").append(updatedOn)
		.append(",userName=").append(userName)
		.append(",partitionMonth=").append(partitionMonth)
		.append(",memo4=").append(memo4)
		.append(",versionOptimizedLock=").append(versionOptimizedLock)
		.append(",transId=").append(transId)
		.append(",productName=").append(productName)
		.append(",total=").append(total)
		.append(",id=").append(id)
		.append(",rType=").append(rType)
		.append(",memo3=").append(memo3)
		.append(",remainderTotal=").append(remainderTotal)
		.append(",merchantId=").append(merchantId)
		.append(",memo2=").append(memo2)
		.append(",status=").append(status)
		.append("]");
		return builder.toString();
	}
	
	public static enum RedpacketInfoEnum{
		STATUS_OK("0","正常可领取状态"),
		STATUS_END("1","领取完毕"),
		STATUS_OVERDUE("2","过期退款状态"),
		REMAINDER_AMT("remainderAmt","红包剩余金额"),
		MEMO1("memo1","预留字段1"),
		PSID("psid","PSID"),
		PS_TRANS_ID("psTransId","业务线流水号"),
		ACCOUNT_ID("accountId","账户ID"),
		EXPIRE_TIME("expireTime","红包过期时间"),
		REFUND_AMT("refundAmt","退款金额"),
		AMT("amt","红包金额"),
		CREATED_ON("createdOn","创建时间"),
		PRODUCT_ID("productId","产品id"),
		UPDATED_ON("updatedOn","更新时间"),
		USER_NAME("userName","发红包账户名"),
		PARTITION_MONTH("partitionMonth","按月分区"),
		MEMO4("memo4","预留字段4"),
		VERSION_OPTIMIZED_LOCK("versionOptimizedLock","乐观锁"),
		TRANS_ID("transId","钱包流水号"),
		PRODUCT_NAME("productName","产品名称"),
		TOTAL("total","红包个数"),
		ID("id","红包编号"),
		R_TYPE("rType","1:可提现红包;2:不可体现红包"),
		MEMO3("memo3","预留字段3"),
		REMAINDER_TOTAL("remainderTotal","红包剩余个数"),
		MERCHANT_ID("merchantId","业务线ID"),
		MEMO2("memo2","预留字段2"),
		STATUS("status","红包状态0:正常可领取状态;1:领取完毕状态;2:过期退款状态");
		private String fieldName;
		private String remark;
		RedpacketInfoEnum(String fieldName,String remark){
			this.fieldName = fieldName;
			this.remark = remark;
		}
		
		public String get(){
			return this.fieldName;
		}
		
		public String getRemark(){
			return this.remark;
		}
	}
	
}
