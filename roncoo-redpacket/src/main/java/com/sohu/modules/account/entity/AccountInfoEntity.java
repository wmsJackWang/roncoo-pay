package com.sohu.modules.account.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-24 14:05:43
 */
public class AccountInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//没有描述
	private Long id;
	//没有描述
	private BigDecimal accountAmt;
	//没有描述
	private String salt;
	//没有描述
	private Integer verityLevel;
	//没有描述
	private String realName;
	//没有描述
	private Integer accountRole;
	//没有描述
	private String credentialsNumber;
	//没有描述
	private Timestamp updatedOn;
	//没有描述
	private String userName;
	//没有描述
	private BigDecimal onwayAmt;
	//没有描述
	private Long versionOptimizedLock;
	//没有描述
	private String bindEmail;
	//没有描述
	private Integer accountStatus;
	//没有描述
	private String bindMobile;
	//没有描述
	private Timestamp lastLoginTime;
	//没有描述
	private Integer accountType;
	//没有描述
	private BigDecimal totalSubsidyAmt;
	//没有描述
	private Timestamp freezeTime;
	//没有描述
	private Timestamp createdOn;
	//没有描述
	private BigDecimal withdrawAmt;
	//没有描述
	private Long merchantId;
	//没有描述
	private String password;
	//没有描述
	private String lastIp;

	/**
	 * 设置：没有描述
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：没有描述
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：没有描述
	 */
	public void setAccountAmt(BigDecimal accountAmt) {
		this.accountAmt = accountAmt;
	}
	/**
	 * 获取：没有描述
	 */
	public BigDecimal getAccountAmt() {
		return accountAmt;
	}
	/**
	 * 设置：没有描述
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * 获取：没有描述
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * 设置：没有描述
	 */
	public void setVerityLevel(Integer verityLevel) {
		this.verityLevel = verityLevel;
	}
	/**
	 * 获取：没有描述
	 */
	public Integer getVerityLevel() {
		return verityLevel;
	}
	/**
	 * 设置：没有描述
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 获取：没有描述
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 设置：没有描述
	 */
	public void setAccountRole(Integer accountRole) {
		this.accountRole = accountRole;
	}
	/**
	 * 获取：没有描述
	 */
	public Integer getAccountRole() {
		return accountRole;
	}
	/**
	 * 设置：没有描述
	 */
	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}
	/**
	 * 获取：没有描述
	 */
	public String getCredentialsNumber() {
		return credentialsNumber;
	}
	/**
	 * 设置：没有描述
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	/**
	 * 获取：没有描述
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	/**
	 * 设置：没有描述
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：没有描述
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：没有描述
	 */
	public void setOnwayAmt(BigDecimal onwayAmt) {
		this.onwayAmt = onwayAmt;
	}
	/**
	 * 获取：没有描述
	 */
	public BigDecimal getOnwayAmt() {
		return onwayAmt;
	}
	/**
	 * 设置：没有描述
	 */
	public void setVersionOptimizedLock(Long versionOptimizedLock) {
		this.versionOptimizedLock = versionOptimizedLock;
	}
	/**
	 * 获取：没有描述
	 */
	public Long getVersionOptimizedLock() {
		return versionOptimizedLock;
	}
	/**
	 * 设置：没有描述
	 */
	public void setBindEmail(String bindEmail) {
		this.bindEmail = bindEmail;
	}
	/**
	 * 获取：没有描述
	 */
	public String getBindEmail() {
		return bindEmail;
	}
	/**
	 * 设置：没有描述
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * 获取：没有描述
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}
	/**
	 * 设置：没有描述
	 */
	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}
	/**
	 * 获取：没有描述
	 */
	public String getBindMobile() {
		return bindMobile;
	}
	/**
	 * 设置：没有描述
	 */
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 获取：没有描述
	 */
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * 设置：没有描述
	 */
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	/**
	 * 获取：没有描述
	 */
	public Integer getAccountType() {
		return accountType;
	}
	/**
	 * 设置：没有描述
	 */
	public void setTotalSubsidyAmt(BigDecimal totalSubsidyAmt) {
		this.totalSubsidyAmt = totalSubsidyAmt;
	}
	/**
	 * 获取：没有描述
	 */
	public BigDecimal getTotalSubsidyAmt() {
		return totalSubsidyAmt;
	}
	/**
	 * 设置：没有描述
	 */
	public void setFreezeTime(Timestamp freezeTime) {
		this.freezeTime = freezeTime;
	}
	/**
	 * 获取：没有描述
	 */
	public Timestamp getFreezeTime() {
		return freezeTime;
	}
	/**
	 * 设置：没有描述
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * 获取：没有描述
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	/**
	 * 设置：没有描述
	 */
	public void setWithdrawAmt(BigDecimal withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	/**
	 * 获取：没有描述
	 */
	public BigDecimal getWithdrawAmt() {
		return withdrawAmt;
	}
	/**
	 * 设置：没有描述
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：没有描述
	 */
	public Long getMerchantId() {
		return merchantId;
	}
	/**
	 * 设置：没有描述
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：没有描述
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：没有描述
	 */
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	/**
	 * 获取：没有描述
	 */
	public String getLastIp() {
		return lastIp;
	}
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("com.sohu.entity.AccountInfoEntity [")
		.append(",id=").append(id)
		.append(",accountAmt=").append(accountAmt)
		.append(",salt=").append(salt)
		.append(",verityLevel=").append(verityLevel)
		.append(",realName=").append(realName)
		.append(",accountRole=").append(accountRole)
		.append(",credentialsNumber=").append(credentialsNumber)
		.append(",updatedOn=").append(updatedOn)
		.append(",userName=").append(userName)
		.append(",onwayAmt=").append(onwayAmt)
		.append(",versionOptimizedLock=").append(versionOptimizedLock)
		.append(",bindEmail=").append(bindEmail)
		.append(",accountStatus=").append(accountStatus)
		.append(",bindMobile=").append(bindMobile)
		.append(",lastLoginTime=").append(lastLoginTime)
		.append(",accountType=").append(accountType)
		.append(",totalSubsidyAmt=").append(totalSubsidyAmt)
		.append(",freezeTime=").append(freezeTime)
		.append(",createdOn=").append(createdOn)
		.append(",withdrawAmt=").append(withdrawAmt)
		.append(",merchantId=").append(merchantId)
		.append(",password=").append(password)
		.append(",lastIp=").append(lastIp)
		.append("]");
		return builder.toString();
	}
	
	public static enum AccountInfoEnum{
		ID("id","没有描述"),
		ACCOUNT_AMT("accountAmt","没有描述"),
		SALT("salt","没有描述"),
		VERITY_LEVEL("verityLevel","没有描述"),
		REAL_NAME("realName","没有描述"),
		ACCOUNT_ROLE("accountRole","没有描述"),
		CREDENTIALS_NUMBER("credentialsNumber","没有描述"),
		UPDATED_ON("updatedOn","没有描述"),
		USER_NAME("userName","没有描述"),
		ONWAY_AMT("onwayAmt","没有描述"),
		VERSION_OPTIMIZED_LOCK("versionOptimizedLock","没有描述"),
		BIND_EMAIL("bindEmail","没有描述"),
		ACCOUNT_STATUS("accountStatus","没有描述"),
		BIND_MOBILE("bindMobile","没有描述"),
		LAST_LOGIN_TIME("lastLoginTime","没有描述"),
		ACCOUNT_TYPE("accountType","没有描述"),
		TOTAL_SUBSIDY_AMT("totalSubsidyAmt","没有描述"),
		FREEZE_TIME("freezeTime","没有描述"),
		CREATED_ON("createdOn","没有描述"),
		WITHDRAW_AMT("withdrawAmt","没有描述"),
		MERCHANT_ID("merchantId","没有描述"),
		PASSWORD("password","没有描述"),
		LAST_IP("lastIp","没有描述");
		private String fieldName;
		private String remark;
		AccountInfoEnum(String fieldName,String remark){
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
