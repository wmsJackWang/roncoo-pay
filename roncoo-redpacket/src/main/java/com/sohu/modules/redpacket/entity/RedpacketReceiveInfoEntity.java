package com.sohu.modules.redpacket.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.sohu.common.validator.group.AddGroup;
import com.sohu.common.validator.group.UpdateGroup;
import com.sohu.entity.base.ParameterEntity;



/**
 * 红包领取记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
public class RedpacketReceiveInfoEntity extends ParameterEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Size(min=1,max=200, message = "回调地址长度只能为1-200位",groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "回调地址不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String callbackUrl;
	//ID
	private Long id;
	//红包id
	@NotNull(message = "红包编号不能为",groups = {AddGroup.class, UpdateGroup.class})
	@Size(min=1,max=32, message = "红包编号长度只能为1-32位",groups = {AddGroup.class, UpdateGroup.class})
	private String frId;
	//包账户名
	@NotNull(message = "用户名不能为空",groups = {AddGroup.class, UpdateGroup.class})
	@Size(min=1,max=64, message = "用户名长度只能为1-64位",groups = {AddGroup.class, UpdateGroup.class})
	private String userName;
	//预留字段1
	private String memo1;
	//PSID
	@NotNull(message = "psid不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private Integer psid;
	//业务线流水号
	@NotNull(message = "业务线流水号不能为空",groups = {AddGroup.class, UpdateGroup.class})
	@Size(min=1,max=32, message = "业务线流水号长度只能为1-32位",groups = {AddGroup.class, UpdateGroup.class})
	private String psTransId;
	//更新时间
	private Timestamp updatedOn;
	//预留字段4
	private String memo4;
	//账户ID
	private Long accountId;
	//乐观锁
	private Long versionOptimizedLock;
	//领取金额
	@NotNull(message = "领取金额不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private BigDecimal amt;
	//创建时间
	private Timestamp createdOn;
	//1:可提现红包;0:不可体现红包
	@Range(min=0,max=1,message = "红包类型错误",groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "红包类型不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private Integer rType ;
	//预留字段3
	private String memo3;
	//业务线ID
	@NotNull(message = "业务线id不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private Long merchantId;
	//预留字段2
	private String memo2;
	//产品id
	@NotNull(message = "产品id不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private Integer productId;
	private BigDecimal planAmt;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：红包id
	 */
	public void setFrId(String frId) {
		this.frId = frId;
	}
	/**
	 * 获取：红包id
	 */
	public String getFrId() {
		return frId;
	}
	/**
	 * 设置：包账户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：包账户名
	 */
	public String getUserName() {
		return userName;
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
	 * 设置：领取金额
	 */
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	/**
	 * 获取：领取金额
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
	 * 设置：1:可提现红包;2:不可体现红包
	 */
	public void setrType(Integer rType) {
		this.rType = rType;
	}
	/**
	 * 获取：1:可提现红包;2:不可体现红包
	 */
	public Integer getrType() {
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
	 * 设置：业务线ID
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：业务线ID
	 */
	public Long getMerchantId() {
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
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("com.sohu.entity.RedpacketReceiveInfoEntity [")
		.append(",id=").append(id)
		.append(",planAmt=").append(getPlanAmt())
		.append(",frId=").append(frId)
		.append(",userName=").append(userName)
		.append(",memo1=").append(memo1)
		.append(",psid=").append(psid)
		.append(",psTransId=").append(psTransId)
		.append(",updatedOn=").append(updatedOn)
		.append(",memo4=").append(memo4)
		.append(",accountId=").append(accountId)
		.append(",versionOptimizedLock=").append(versionOptimizedLock)
		.append(",amt=").append(amt)
		.append(",createdOn=").append(createdOn)
		.append(",rType=").append(rType)
		.append(",memo3=").append(memo3)
		.append(",merchantId=").append(merchantId)
		.append(",memo2=").append(memo2)
		.append(",productId=").append(productId)
		.append(",sign=").append(getSign())
		.append("]");
		return builder.toString();
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public BigDecimal getPlanAmt() {
		return planAmt;
	}
	public void setPlanAmt(BigDecimal planAmt) {
		this.planAmt = planAmt;
	}

	public static enum RedpacketReceiveInfoEnum{
		R_TYPE_WITHDRAW("1","可提现类型"),
		R_TYPE_NUWITHDRAW("0","不可提现类型"),
		ID("id","ID"),
		CALLBACK_URL("callbackUrl","回调地址不能为空"),
		FR_ID("frId","红包id"),
		USER_NAME("userName","包账户名"),
		MEMO1("memo1","预留字段1"),
		PSID("psid","PSID"),
		PS_TRANS_ID("psTransId","业务线流水号"),
		UPDATED_ON("updatedOn","更新时间"),
		MEMO4("memo4","预留字段4"),
		ACCOUNT_ID("accountId","账户ID"),
		VERSION_OPTIMIZED_LOCK("versionOptimizedLock","乐观锁"),
		AMT("amt","领取金额"),
		PLAN_AMT("planAmt","领取金额"),
		CREATED_ON("createdOn","创建时间"),
		R_TYPE("rType","1:可提现红包;0:不可体现红包"),
		MEMO3("memo3","预留字段3"),
		MERCHANT_ID("merchantId","业务线ID"),
		MEMO2("memo2","预留字段2"),
		PRODUCT_ID("productId","产品id"),
		SIGN("sign","sign");
		private String fieldName;
		private String remark;
		RedpacketReceiveInfoEnum(String fieldName,String remark){
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
