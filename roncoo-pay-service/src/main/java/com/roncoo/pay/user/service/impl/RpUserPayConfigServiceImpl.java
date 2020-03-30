/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roncoo.pay.common.core.enums.JDPayExtEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.user.dao.RpUserPayConfigDao;
import com.roncoo.pay.user.entity.RpPayProduct;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.entity.RpUserPayExtInfo;
import com.roncoo.pay.user.entity.RpUserPayInfo;
import com.roncoo.pay.user.exception.PayBizException;
import com.roncoo.pay.user.service.RpPayProductService;
import com.roncoo.pay.user.service.RpPayWayService;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.user.service.RpUserPayExtInfoService;
import com.roncoo.pay.user.service.RpUserPayInfoService;

/**
 * 用户支付配置service实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("rpUserPayConfigService")
public class RpUserPayConfigServiceImpl implements RpUserPayConfigService{
	
	private static final Logger log = LoggerFactory.getLogger(RpUserPayConfigServiceImpl.class);


	@Autowired
	private RpUserPayConfigDao rpUserPayConfigDao;
	@Autowired
	private RpPayProductService rpPayProductService;
	@Autowired
	private RpPayWayService rpPayWayService;
	@Autowired
	private RpUserPayInfoService rpUserPayInfoService;
	@Autowired
	private RpUserPayExtInfoService rpUserPayExtInfoService;
	
	@Override
	public void saveData(RpUserPayConfig rpUserPayConfig) {
		rpUserPayConfigDao.insert(rpUserPayConfig);
	}

	@Override
	public void updateData(RpUserPayConfig rpUserPayConfig) {
		rpUserPayConfigDao.update(rpUserPayConfig);
	}

	@Override
	public RpUserPayConfig getDataById(String id) {
		return rpUserPayConfigDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpUserPayConfig rpUserPayConfig) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productCode", rpUserPayConfig.getProductCode());
		paramMap.put("userNo", rpUserPayConfig.getUserNo());
		paramMap.put("userName", rpUserPayConfig.getUserName());
		paramMap.put("productName", rpUserPayConfig.getProductName());
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpUserPayConfigDao.listPage(pageParam, paramMap);
	}

	/**
	 * 根据商户编号获取已生效的支付配置
	 *
	 * @param userNo
	 * @return
	 */
	@Override
	public RpUserPayConfig getByUserNo(String userNo) {
		return rpUserPayConfigDao.getByUserNo(userNo, PublicEnum.YES.name());
	}
	
	/**
	 * 根据商户编号获取支付配置
	 * @param userNo
	 * @param auditStatus
	 * @return
	 */
	@Override
	public RpUserPayConfig getByUserNo(String userNo, String auditStatus){
		return rpUserPayConfigDao.getByUserNo(userNo, auditStatus);
	}
	
	
	/**
	 * 根据支付产品获取已生效数据
	 */
	@Override
	public List<RpUserPayConfig> listByProductCode(String productCode){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productCode", productCode);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		paramMap.put("auditStatus", PublicEnum.YES.name());
		return rpUserPayConfigDao.listBy(paramMap);
	}
	
	/**
	 * 根据支付产品获取数据
	 */
	@Override
	public List<RpUserPayConfig> listByProductCode(String productCode, String auditStatus){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productCode", productCode);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		paramMap.put("auditStatus", auditStatus);
		return rpUserPayConfigDao.listBy(paramMap);
	}
	
	/**
	 * 创建用户支付配置
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay,
			String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey,
			String ali_partner, String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey)  throws PayBizException{

		createUserPayConfig( userNo,  userName,  productCode,  productName, riskDay,
				 fundIntoType,  isAutoSett,  appId,  merchantId,  partnerKey,
				 ali_partner,  ali_sellerId,  ali_key,  ali_appid,  ali_rsaPrivateKey,  ali_rsaPublicKey ,  null ,  null);
	}

	/**
	 * 创建用户支付配置
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay,
			String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey,
			String ali_partner, String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey , String securityRating , String merchantServerIp)  throws PayBizException{

		RpUserPayConfig payConfig = rpUserPayConfigDao.getByUserNo(userNo, null);
		if(payConfig != null){
			throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_EXIST,"用户支付配置已存在");
		}

		RpUserPayConfig rpUserPayConfig = new RpUserPayConfig();
		rpUserPayConfig.setUserNo(userNo);
		rpUserPayConfig.setUserName(userName);
		rpUserPayConfig.setProductCode(productCode);
		rpUserPayConfig.setProductName(productName);
		rpUserPayConfig.setStatus(PublicStatusEnum.ACTIVE.name());
		rpUserPayConfig.setAuditStatus(PublicEnum.YES.name());
		rpUserPayConfig.setRiskDay(riskDay);
		rpUserPayConfig.setFundIntoType(fundIntoType);
		rpUserPayConfig.setIsAutoSett(isAutoSett);
		rpUserPayConfig.setPayKey(StringUtil.get32UUID());
		rpUserPayConfig.setPaySecret(StringUtil.get32UUID());
		rpUserPayConfig.setId(StringUtil.get32UUID());
		rpUserPayConfig.setSecurityRating(securityRating);//安全等级
		rpUserPayConfig.setMerchantServerIp(merchantServerIp);
		saveData(rpUserPayConfig);

		//查询支付产品下有哪些支付方式
		List<RpPayWay> payWayList = rpPayWayService.listByProductCode(productCode);
		Map<String, String> map = new HashMap<String, String>();
		//过滤重复数据
		for(RpPayWay payWay : payWayList){
	        map.put(payWay.getPayWayCode(), payWay.getPayWayName());
		}

		for (String key : map.keySet()) {
			if(key.equals(PayWayEnum.WEIXIN.name())){
				//创建用户第三方支付信息
				RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
				if(rpUserPayInfo == null){
					rpUserPayInfo = new RpUserPayInfo();
					rpUserPayInfo.setId(StringUtil.get32UUID());
					rpUserPayInfo.setCreateTime(new Date());
					rpUserPayInfo.setAppId(appId);
					rpUserPayInfo.setMerchantId(merchantId);
					rpUserPayInfo.setPartnerKey(partnerKey);
					rpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
					rpUserPayInfo.setUserNo(userNo);
					rpUserPayInfo.setUserName(userName);
					rpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
					rpUserPayInfoService.saveData(rpUserPayInfo);
				}else{
					rpUserPayInfo.setEditTime(new Date());
					rpUserPayInfo.setAppId(appId);
					rpUserPayInfo.setMerchantId(merchantId);
					rpUserPayInfo.setPartnerKey(partnerKey);
					rpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
					rpUserPayInfoService.updateData(rpUserPayInfo);
				}

			}else if(key.equals(PayWayEnum.ALIPAY.name())){
				//创建用户第三方支付信息
				RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
				if(rpUserPayInfo == null){
					rpUserPayInfo = new RpUserPayInfo();
					rpUserPayInfo.setId(StringUtil.get32UUID());
					rpUserPayInfo.setCreateTime(new Date());
					rpUserPayInfo.setAppId(ali_partner);
					rpUserPayInfo.setMerchantId(ali_sellerId);
					rpUserPayInfo.setPartnerKey(ali_key);
					rpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
					rpUserPayInfo.setUserNo(userNo);
					rpUserPayInfo.setUserName(userName);
					rpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
					rpUserPayInfo.setOfflineAppId(ali_appid);
					rpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
					rpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
					rpUserPayInfoService.saveData(rpUserPayInfo);
				}else{
					rpUserPayInfo.setEditTime(new Date());
					rpUserPayInfo.setAppId(ali_partner);
					rpUserPayInfo.setMerchantId(ali_sellerId);
					rpUserPayInfo.setPartnerKey(ali_key);
					rpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
					rpUserPayInfo.setOfflineAppId(ali_appid);
					rpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
					rpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
					rpUserPayInfoService.updateData(rpUserPayInfo);
				}
			}
		}



	}

	/**
	 * 删除支付产品
	 * @param userNo
	 */
	@Override
	public void deleteUserPayConfig(String userNo) throws PayBizException{
		
		RpUserPayConfig rpUserPayConfig = rpUserPayConfigDao.getByUserNo(userNo, null);
		if(rpUserPayConfig == null){
			throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST,"用户支付配置不存在");
		}
		
		rpUserPayConfig.setStatus(PublicStatusEnum.UNACTIVE.name());
		rpUserPayConfig.setEditTime(new Date());
		updateData(rpUserPayConfig);
	}
	
	/**
	 * 修改用户支付配置
	 */
	@Override
	public void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay, String fundIntoType,
			String isAutoSett, String appId, String merchantId, String partnerKey,
			String ali_partner, String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey)  throws PayBizException{

			updateUserPayConfig( userNo,  productCode,  productName,  riskDay,  fundIntoType,
				 isAutoSett,  appId,  merchantId,  partnerKey,
				 ali_partner,  ali_sellerId,  ali_key,  ali_appid,  ali_rsaPrivateKey,  ali_rsaPublicKey  ,  null ,  null ,  null, null );
	}
	/**
	 * 修改用户支付配置
	 */
	@Transactional
	@Override
	public void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay, String fundIntoType,
			String isAutoSett, String appId, String merchantId, String partnerKey,
			String ali_partner, String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey  ,
			String securityRating , String merchantServerIp ,String JD_SELLER_ID ,Map<String, Map<String ,String>> JD_Prams)  throws PayBizException{
//		/ , String JD_DES_SCERET_KEY , String JD_MD5_SCERET_KEY,String JD_SELLER_ID ,String JD_RSA_SCERET_KEY
		
		RpUserPayConfig rpUserPayConfig = rpUserPayConfigDao.getByUserNo(userNo, null);
		if(rpUserPayConfig == null){
			throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST,"用户支付配置不存在");
		}

		rpUserPayConfig.setProductCode(productCode);
		rpUserPayConfig.setProductName(productName);
		rpUserPayConfig.setRiskDay(riskDay);
		rpUserPayConfig.setFundIntoType(fundIntoType);
		rpUserPayConfig.setIsAutoSett(isAutoSett);
		rpUserPayConfig.setEditTime(new Date());
		rpUserPayConfig.setSecurityRating(securityRating);//安全等级
		rpUserPayConfig.setMerchantServerIp(merchantServerIp);
		updateData(rpUserPayConfig);

		//查询支付产品下有哪些支付方式
		List<RpPayWay> payWayList = rpPayWayService.listByProductCode(productCode);
		Map<String, String> map = new HashMap<String, String>();
		//过滤重复数据
		for(RpPayWay payWay : payWayList){
			map.put(payWay.getPayWayCode(), payWay.getPayWayName());
		}

		for (String key : map.keySet()) {
			if(key.equals(PayWayEnum.WEIXIN.name())){
				//创建用户第三方支付信息
				RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
				if(rpUserPayInfo == null){
					rpUserPayInfo = new RpUserPayInfo();
					rpUserPayInfo.setId(StringUtil.get32UUID());
					rpUserPayInfo.setCreateTime(new Date());
					rpUserPayInfo.setAppId(appId);
					rpUserPayInfo.setMerchantId(merchantId);
					rpUserPayInfo.setPartnerKey(partnerKey);
					rpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
					rpUserPayInfo.setUserNo(userNo);
					rpUserPayInfo.setUserName(rpUserPayConfig.getUserName());
					rpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
					rpUserPayInfoService.saveData(rpUserPayInfo);
				}else{
					rpUserPayInfo.setEditTime(new Date());
					rpUserPayInfo.setAppId(appId);
					rpUserPayInfo.setMerchantId(merchantId);
					rpUserPayInfo.setPartnerKey(partnerKey);
					rpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
					rpUserPayInfoService.updateData(rpUserPayInfo);
				}

			}else if(key.equals(PayWayEnum.ALIPAY.name())){
				//创建用户第三方支付信息
				RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
				if(rpUserPayInfo == null){
					rpUserPayInfo = new RpUserPayInfo();
					rpUserPayInfo.setId(StringUtil.get32UUID());
					rpUserPayInfo.setCreateTime(new Date());
					rpUserPayInfo.setAppId(ali_appid);
					rpUserPayInfo.setMerchantId(ali_sellerId);
					rpUserPayInfo.setPartnerKey(ali_key);
					rpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
					rpUserPayInfo.setUserNo(userNo);
					rpUserPayInfo.setUserName(rpUserPayConfig.getUserName());
					rpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
					rpUserPayInfo.setOfflineAppId(ali_appid);
					rpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
					rpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
					rpUserPayInfoService.saveData(rpUserPayInfo);
				}else{
					rpUserPayInfo.setEditTime(new Date());
					log.info("ali_appid:"+ali_appid);
					rpUserPayInfo.setAppId(ali_appid);
					rpUserPayInfo.setMerchantId(ali_sellerId);
					rpUserPayInfo.setPartnerKey(ali_key);
					rpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
					rpUserPayInfo.setOfflineAppId(ali_appid);
					rpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
					rpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
					rpUserPayInfoService.updateData(rpUserPayInfo);
				}
			}else if(key.equals(PayWayEnum.JINGDONG.name())){
				//创建用户第三方支付信息
				RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.JINGDONG.name());
				if(rpUserPayInfo == null){
					rpUserPayInfo = new RpUserPayInfo();
					rpUserPayInfo.setId(StringUtil.get32UUID());
					rpUserPayInfo.setCreateTime(new Date());
					rpUserPayInfo.setAppId(JD_SELLER_ID);
					rpUserPayInfo.setMerchantId(JD_SELLER_ID);
					rpUserPayInfo.setUserNo(userNo);
					rpUserPayInfo.setUserName(rpUserPayConfig.getUserName());
					rpUserPayInfo.setPayWayCode(PayWayEnum.JINGDONG.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.JINGDONG.getDesc());
					rpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
					rpUserPayInfoService.saveData(rpUserPayInfo);
					
					
					
				}else {

					rpUserPayInfo.setEditTime(new Date());
					rpUserPayInfo.setPayWayCode(PayWayEnum.JINGDONG.name());
					rpUserPayInfo.setPayWayName(PayWayEnum.JINGDONG.getDesc());
//					rpUserPayInfo.setAppId(JD_CLUB_NUMBER_CARD_ID);
					rpUserPayInfo.setMerchantId(JD_SELLER_ID);
					rpUserPayInfoService.updateData(rpUserPayInfo);
				}
				//京东支付扩展信息
				RpUserPayExtInfo payExtInfo1 = new RpUserPayExtInfo();
				RpUserPayExtInfo payExtInfo2 = new RpUserPayExtInfo();
				RpUserPayExtInfo payExtInfo3 = new RpUserPayExtInfo();
				RpUserPayExtInfo payExtInfo4 = new RpUserPayExtInfo();
				RpUserPayExtInfo payExtInfo5 = new RpUserPayExtInfo();
	
				
				if(JD_Prams.get(JDPayExtEnum.JD_DES_SCERET_KEY.name())!=null)
				{
					if(StringUtil.isEmpty(JD_Prams.get(JDPayExtEnum.JD_DES_SCERET_KEY.name()).get("ID")))
					{
						payExtInfo1.setId(StringUtil.get32UUID());
						payExtInfo1.setCreateTime(new Date());
						payExtInfo1.setRp_user_pay_info_id(rpUserPayInfo.getId());
						payExtInfo1.setPay_way_code(PayWayEnum.JINGDONG.name());
						payExtInfo1.setPay_way_name(PayWayEnum.JINGDONG.getDesc());
						payExtInfo1.setType_code(JDPayExtEnum.JD_DES_SCERET_KEY.name());
						payExtInfo1.setType_desc(JDPayExtEnum.JD_DES_SCERET_KEY.getDesc());
						payExtInfo1.setContent(JD_Prams.get(JDPayExtEnum.JD_DES_SCERET_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.saveData(payExtInfo1);
					}
					else {
						log.info("ID:"+JD_Prams.get(JDPayExtEnum.JD_DES_SCERET_KEY.name()).get("ID"));
						payExtInfo1.setId(JD_Prams.get(JDPayExtEnum.JD_DES_SCERET_KEY.name()).get("ID"));
						payExtInfo1.setEditTime(new Date());
						payExtInfo1.setType_desc(JDPayExtEnum.JD_DES_SCERET_KEY.getDesc());
						payExtInfo1.setContent(JD_Prams.get(JDPayExtEnum.JD_DES_SCERET_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.updateData(payExtInfo1);
						
					}
					
				}
				
				if(JD_Prams.get(JDPayExtEnum.JD_MD5_SCERET_KEY.name())!=null)
				{
					if(StringUtil.isEmpty(JD_Prams.get(JDPayExtEnum.JD_MD5_SCERET_KEY.name()).get("ID")))
					{
						payExtInfo2.setId(StringUtil.get32UUID());
						payExtInfo2.setCreateTime(new Date());
						payExtInfo2.setRp_user_pay_info_id(rpUserPayInfo.getId());
						payExtInfo2.setPay_way_code(PayWayEnum.JINGDONG.name());
						payExtInfo2.setPay_way_name(PayWayEnum.JINGDONG.getDesc());
						payExtInfo2.setType_code(JDPayExtEnum.JD_MD5_SCERET_KEY.name());
						payExtInfo2.setType_desc(JDPayExtEnum.JD_MD5_SCERET_KEY.getDesc());
						payExtInfo2.setContent(JD_Prams.get(JDPayExtEnum.JD_MD5_SCERET_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.saveData(payExtInfo2);
					}
					else {
						log.info("ID:"+JD_Prams.get(JDPayExtEnum.JD_MD5_SCERET_KEY.name()).get("ID"));
						payExtInfo2.setId(JD_Prams.get(JDPayExtEnum.JD_MD5_SCERET_KEY.name()).get("ID"));
						payExtInfo2.setEditTime(new Date());
						payExtInfo2.setType_desc(JDPayExtEnum.JD_MD5_SCERET_KEY.getDesc());
						payExtInfo2.setContent(JD_Prams.get(JDPayExtEnum.JD_MD5_SCERET_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.updateData(payExtInfo2);
						
					}
					
				}
//				JD_CLUB_NUMBER_CARD_ID
				
				if(JD_Prams.get(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name())!=null)
				{
					if(StringUtil.isEmpty(JD_Prams.get(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name()).get("ID")))
					{
						payExtInfo3.setId(StringUtil.get32UUID());
						payExtInfo3.setCreateTime(new Date());
						payExtInfo3.setRp_user_pay_info_id(rpUserPayInfo.getId());
						payExtInfo3.setPay_way_code(PayWayEnum.JINGDONG.name());
						payExtInfo3.setPay_way_name(PayWayEnum.JINGDONG.getDesc());
						payExtInfo3.setType_code(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name());
						payExtInfo3.setType_desc(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.getDesc());
						payExtInfo3.setContent(JD_Prams.get(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name()).get("CONTENT"));
						rpUserPayExtInfoService.saveData(payExtInfo3);
					}
					else {
						log.info("ID:"+JD_Prams.get(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name()).get("ID"));
						payExtInfo3.setId(JD_Prams.get(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name()).get("ID"));
						payExtInfo3.setEditTime(new Date());
						payExtInfo3.setType_desc(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.getDesc());
						payExtInfo3.setContent(JD_Prams.get(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name()).get("CONTENT"));
						rpUserPayExtInfoService.updateData(payExtInfo3);
						
					}
					
				}

				if(JD_Prams.get(JDPayExtEnum.JD_RSA_SCERET_KEY.name())!=null)
				{
					if(StringUtil.isEmpty(JD_Prams.get(JDPayExtEnum.JD_RSA_SCERET_KEY.name()).get("ID")))
					{
						payExtInfo4.setId(StringUtil.get32UUID());
						payExtInfo4.setCreateTime(new Date());
						payExtInfo4.setRp_user_pay_info_id(rpUserPayInfo.getId());
						payExtInfo4.setPay_way_code(PayWayEnum.JINGDONG.name());
						payExtInfo4.setPay_way_name(PayWayEnum.JINGDONG.getDesc());
						payExtInfo4.setType_code(JDPayExtEnum.JD_RSA_SCERET_KEY.name());
						payExtInfo4.setType_desc(JDPayExtEnum.JD_RSA_SCERET_KEY.getDesc());
						payExtInfo4.setContent(JD_Prams.get(JDPayExtEnum.JD_RSA_SCERET_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.saveData(payExtInfo4);
					}
					else {
						log.info("ID:"+JD_Prams.get(JDPayExtEnum.JD_RSA_SCERET_KEY.name()).get("ID"));
						payExtInfo4.setId(JD_Prams.get(JDPayExtEnum.JD_RSA_SCERET_KEY.name()).get("ID"));
						payExtInfo4.setEditTime(new Date());
						payExtInfo4.setType_desc(JDPayExtEnum.JD_RSA_SCERET_KEY.getDesc());
						payExtInfo4.setContent(JD_Prams.get(JDPayExtEnum.JD_RSA_SCERET_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.updateData(payExtInfo4);
						
					}
					
				}
				
				if(JD_Prams.get(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name())!=null)
				{
					if(StringUtil.isEmpty(JD_Prams.get(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name()).get("ID")))
					{
						payExtInfo5.setId(StringUtil.get32UUID());
						payExtInfo5.setCreateTime(new Date());
						payExtInfo5.setRp_user_pay_info_id(rpUserPayInfo.getId());
						payExtInfo5.setPay_way_code(PayWayEnum.JINGDONG.name());
						payExtInfo5.setPay_way_name(PayWayEnum.JINGDONG.getDesc());
						payExtInfo5.setType_code(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name());
						payExtInfo5.setType_desc(JDPayExtEnum.JD_RSA_PUBLIC_KEY.getDesc());
						payExtInfo5.setContent(JD_Prams.get(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.saveData(payExtInfo5);
					}
					else {
						log.info("ID:"+JD_Prams.get(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name()).get("ID"));
						payExtInfo5.setId(JD_Prams.get(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name()).get("ID"));
						payExtInfo5.setEditTime(new Date());
						payExtInfo5.setType_desc(JDPayExtEnum.JD_RSA_PUBLIC_KEY.getDesc());
						payExtInfo5.setContent(JD_Prams.get(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name()).get("CONTENT"));
						rpUserPayExtInfoService.updateData(payExtInfo5);
						
					}
					
				}
				
			}
		}
	}

	/**
	 * 审核
	 * @param userNo
	 * @param auditStatus
	 */
	@Override
	public void audit(String userNo, String auditStatus){
		RpUserPayConfig rpUserPayConfig = getByUserNo(userNo, null);
		if(rpUserPayConfig == null){
			throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST,"支付配置不存在！");
		}
		
		if(auditStatus.equals(PublicEnum.YES.name())){
			//检查是否已关联生效的支付产品
			RpPayProduct rpPayProduct = rpPayProductService.getByProductCode(rpUserPayConfig.getProductCode(), PublicEnum.YES.name());
			if(rpPayProduct == null){
				throw new PayBizException(PayBizException.PAY_PRODUCT_IS_NOT_EXIST,"未关联已生效的支付产品，无法操作！");
			}
			
			//检查是否已设置第三方支付信息
		}
		rpUserPayConfig.setAuditStatus(auditStatus);
		rpUserPayConfig.setEditTime(new Date());
		updateData(rpUserPayConfig);
	}
	
	/**
	 * 根据商户key获取已生效的支付配置
	 * @param payKey
	 * @return
	 */
	public RpUserPayConfig getByPayKey(String payKey){
		log.info("payKey"+payKey);
	    Map<String , Object> paramMap = new HashMap<String , Object>();
	    paramMap.put("payKey", payKey);
	    paramMap.put("status", PublicStatusEnum.ACTIVE.name());
	    paramMap.put("auditStatus", PublicEnum.YES.name());
	    return rpUserPayConfigDao.getBy(paramMap);
	}
}