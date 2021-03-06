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
package com.roncoo.pay.controller.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.enums.JDPayExtEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.enums.SecurityRatingEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.user.entity.RpUserBankAccount;
import com.roncoo.pay.user.entity.RpUserInfo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.entity.RpUserPayExtInfo;
import com.roncoo.pay.user.entity.RpUserPayInfo;
import com.roncoo.pay.user.enums.BankAccountTypeEnum;
import com.roncoo.pay.user.enums.BankCodeEnum;
import com.roncoo.pay.user.enums.CardTypeEnum;
import com.roncoo.pay.user.enums.FundInfoTypeEnum;
import com.roncoo.pay.user.service.RpUserBankAccountService;
import com.roncoo.pay.user.service.RpUserInfoService;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.user.service.RpUserPayExtInfoService;
import com.roncoo.pay.user.service.RpUserPayInfoService;

/**
 * 用户支付设置管理
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Controller
@RequestMapping("/pay/config")
public class UserPayConfigController {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserPayConfigController.class);

	
	
	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;
	@Autowired
	private RpUserInfoService rpUserInfoService;
	@Autowired
	private RpUserPayInfoService rpUserPayInfoService;
	@Autowired
	private RpUserPayExtInfoService rpUserPayExtInfoService;
	@Autowired
	private RpUserBankAccountService rpUserBankAccountService;


	/**
	 * 函数功能说明 ： 查询分页数据
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/list", method ={RequestMethod.POST, RequestMethod.GET})
	public String list(RpUserPayConfig rpUserPayConfig, PageParam pageParam, Model model) {
		PageBean pageBean = rpUserPayConfigService.listPage(pageParam, rpUserPayConfig);
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserPayConfig", rpUserPayConfig);
		return "pay/config/list";
	}
	
	/**
	 * 函数功能说明 ：跳转添加
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:add")
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
		model.addAttribute("SecurityRatingEnum", SecurityRatingEnum.toList());
		return "pay/config/add";
	}
	
	/**
	 * 函数功能说明 ： 保存
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, RpUserPayConfig rpUserPayConfig, DwzAjax dwz) {
		String userNo = request.getParameter("user.userNo");
		String userName = request.getParameter("user.userName");
		String productCode = request.getParameter("product.productCode");
		String productName = request.getParameter("product.productName");
		String securityRating = request.getParameter("securityRating");
		String merchantServerIp = request.getParameter("merchantServerIp");
		String we_appId = request.getParameter("we_appId");
		String we_merchantId = request.getParameter("we_merchantId");
		String we_partnerKey = request.getParameter("we_partnerKey");
		String ali_partner = request.getParameter("ali_partner");
		String ali_key = request.getParameter("ali_key");
		String ali_sellerId = request.getParameter("ali_sellerId");
		String ali_appid = request.getParameter("ali_appid");
		String ali_rsaPrivateKey = request.getParameter("ali_rsaPrivateKey");
		String ali_rsaPublicKey = request.getParameter("ali_rsaPublicKey");

		// 如果是商户且安全等级是MD5+IP白名单 , 则 IP白名单不能为空
		if (SecurityRatingEnum.MD5_IP.name().equals(securityRating)) {
			if (StringUtil.isEmpty(merchantServerIp)) {
				dwz.setStatusCode(DWZ.ERROR);
				dwz.setMessage("商户IP白名单不能为空");
				model.addAttribute("dwz", dwz);
				return DWZ.AJAX_DONE;
			}
		}
		rpUserPayConfigService.createUserPayConfig(userNo, userName, productCode, productName, 
				rpUserPayConfig.getRiskDay(), rpUserPayConfig.getFundIntoType(), rpUserPayConfig.getIsAutoSett(), we_appId
				, we_merchantId, we_partnerKey, ali_partner, ali_sellerId, ali_key, ali_appid, ali_rsaPrivateKey, ali_rsaPublicKey , securityRating , merchantServerIp);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}

	/**
	 * 函数功能说明 ：跳转编辑
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:edit")
	@RequestMapping(value = "/editUI", method = RequestMethod.GET)
	public String editUI(Model model, @RequestParam("userNo") String userNo) {
		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(userNo,null);
		RpUserPayInfo wxUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
		RpUserPayInfo aliUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
		RpUserPayInfo jdUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.JINGDONG.name());
		RpUserPayInfo unionUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.UNION.name());
		
		
		model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
		model.addAttribute("rpUserPayConfig", rpUserPayConfig);
		model.addAttribute("wxUserPayInfo", wxUserPayInfo);
		model.addAttribute("aliUserPayInfo", aliUserPayInfo);
		model.addAttribute("jdUserPayInfo", jdUserPayInfo);
		model.addAttribute("unionUserPayInfo", unionUserPayInfo);
		model.addAttribute("SecurityRatingEnum", SecurityRatingEnum.toList());
		
		/* JD_CLUB_NUMBER_CARD_ID JD_DES_SCERET_KEY  JD_MD5_SCERET_KEY
		 * 京东支付
		 */
		String jdId = null;
		if(jdUserPayInfo !=null)
			 jdId = jdUserPayInfo.getId();
		List<RpUserPayExtInfo>  jdPayExtInfos = rpUserPayExtInfoService.getByUserIdAndPayWayCode( jdId,PayWayEnum.JINGDONG.name());
		if(!StringUtil.isEmpty(jdPayExtInfos))
			for(RpUserPayExtInfo rpUserPayExtInfo : jdPayExtInfos)
			{
				//传入的是扩展信息的内容
				model.addAttribute( rpUserPayExtInfo.getType_code(),rpUserPayExtInfo.getContent());
				//传入的是扩展信息的id
				model.addAttribute( rpUserPayExtInfo.getType_code() +"_ID" , rpUserPayExtInfo.getId());
				log.info("id: "+ rpUserPayExtInfo.getType_code()+"  "+ rpUserPayExtInfo.getId());
			}
		log.info("京东支付的扩展信息查询结果数量：{}" , !StringUtil.isEmpty(jdPayExtInfos)?jdPayExtInfos.size():"空");
		
		/*
		 * 银联支付
		 */
		String unionId = null;
		if(unionUserPayInfo !=null)
			unionId = unionUserPayInfo.getId();
		List<RpUserPayExtInfo>  unionPayExtInfos = rpUserPayExtInfoService.getByUserIdAndPayWayCode( jdId,PayWayEnum.UNION.name());
		if(!StringUtil.isEmpty(unionPayExtInfos))
			for(RpUserPayExtInfo rpUserPayExtInfo : unionPayExtInfos)
				model.addAttribute( rpUserPayExtInfo.getType_code(),rpUserPayExtInfo.getContent());
		
		
		return "pay/config/edit";
	}
	
	/**
	 * 函数功能说明 ： 更新
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:edit")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Model model, RpUserPayConfig rpUserPayConfig, DwzAjax dwz) {
		String productCode = request.getParameter("product.productCode");
		String productName = request.getParameter("product.productName");
		String securityRating = request.getParameter("securityRating");
		String merchantServerIp = request.getParameter("merchantServerIp");
		String we_appId = request.getParameter("we_appId");
		String we_merchantId = request.getParameter("we_merchantId");
		String we_partnerKey = request.getParameter("we_partnerKey");
		String ali_partner = request.getParameter("ali_partner");
		String ali_key = request.getParameter("ali_key");
		String ali_sellerId = request.getParameter("ali_sellerId");
		String ali_appid = request.getParameter("ali_appid");
		log.info("ali_appid: "+ ali_appid);
		String ali_rsaPrivateKey = request.getParameter("ali_rsaPrivateKey");
		String ali_rsaPublicKey = request.getParameter("ali_rsaPublicKey");
		
		/* JD_CLUB_NUMBER_CARD_ID JD_DES_SCERET_KEY  JD_MD5_SCERET_KEY
		 * 京东支付
		 */
		String JD_CLUB_NUMBER_CARD_ID = request.getParameter("JD_CLUB_NUMBER_CARD_ID");
		String JD_DES_SCERET_KEY = request.getParameter("JD_DES_SCERET_KEY");
		String JD_MD5_SCERET_KEY = request.getParameter("JD_MD5_SCERET_KEY");
		String JD_SELLER_ID = request.getParameter("JD_SELLER_ID");
		String JD_RSA_SCERET_KEY = request.getParameter("JD_RSA_SCERET_KEY");
		String JD_RSA_PUBLIC_KEY = request.getParameter("JD_RSA_PUBLIC_KEY");
		
		//获取扩展信息的id，为空则表示是新创建的扩展数据
		String JD_CLUB_NUMBER_CARD_ID_ID = request.getParameter("JD_CLUB_NUMBER_CARD_ID_ID");
		String JD_DES_SCERET_KEY_ID = request.getParameter("JD_DES_SCERET_KEY_ID");
		String JD_MD5_SCERET_KEY_ID = request.getParameter("JD_MD5_SCERET_KEY_ID");
		String JD_RSA_SCERET_KEY_ID = request.getParameter("JD_RSA_SCERET_KEY_ID");
		String JD_RSA_PUBLIC_KEY_ID = request.getParameter("JD_RSA_PUBLIC_KEY_ID");
		
		log.info("JD_DES_SCERET_KEY_ID: "+ JD_DES_SCERET_KEY_ID);
		log.info("JD_MD5_SCERET_KEY_ID: "+ JD_MD5_SCERET_KEY_ID);
		log.info("JD_CLUB_NUMBER_CARD_ID_ID: "+ JD_CLUB_NUMBER_CARD_ID_ID);
		log.info("JD_RSA_SCERET_KEY_ID: "+ JD_RSA_SCERET_KEY_ID);
		
		/*
		 * 银联支付
		 */
		String union_appid = request.getParameter("union_appid");
		String union_mchid = request.getParameter("union_mchid");
		
		

		// 如果是商户且安全等级是MD5+IP白名单 , 则 IP白名单不能为空
		if (SecurityRatingEnum.MD5_IP.name().equals(securityRating)) {
			if (StringUtil.isEmpty(merchantServerIp)) {
				dwz.setStatusCode(DWZ.ERROR);
				dwz.setMessage("商户IP白名单不能为空");
				model.addAttribute("dwz", dwz);
				return DWZ.AJAX_DONE;
			}
		}
		
		
		Map<String, Map<String ,String>> JD_Prams = new HashMap<>();
		
		Map<String ,String> JD_CLUB_NUMBER_CARD_ID_MAP = new HashMap<>();
		JD_CLUB_NUMBER_CARD_ID_MAP.put("CONTENT", JD_CLUB_NUMBER_CARD_ID);
		JD_CLUB_NUMBER_CARD_ID_MAP.put("ID", JD_CLUB_NUMBER_CARD_ID_ID);
		JD_Prams.put(JDPayExtEnum.JD_CLUB_NUMBER_CARD_ID.name(), JD_CLUB_NUMBER_CARD_ID_MAP);
		
		Map<String ,String> JD_DES_SCERET_KEY_MAP = new HashMap<>();
		JD_DES_SCERET_KEY_MAP.put("CONTENT", JD_DES_SCERET_KEY);
		JD_DES_SCERET_KEY_MAP.put("ID", JD_DES_SCERET_KEY_ID);
		JD_Prams.put(JDPayExtEnum.JD_DES_SCERET_KEY.name(), JD_DES_SCERET_KEY_MAP);
		
		Map<String ,String> JD_MD5_SCERET_KEY_MAP = new HashMap<>();
		JD_MD5_SCERET_KEY_MAP.put("CONTENT", JD_MD5_SCERET_KEY);
		JD_MD5_SCERET_KEY_MAP.put("ID", JD_MD5_SCERET_KEY_ID);
		JD_Prams.put(JDPayExtEnum.JD_MD5_SCERET_KEY.name(), JD_MD5_SCERET_KEY_MAP);
		
		Map<String ,String> JD_RSA_SCERET_KEY_MAP = new HashMap<>();
		JD_RSA_SCERET_KEY_MAP.put("CONTENT", JD_RSA_SCERET_KEY);
		JD_RSA_SCERET_KEY_MAP.put("ID", JD_RSA_SCERET_KEY_ID);
		JD_Prams.put(JDPayExtEnum.JD_RSA_SCERET_KEY.name(), JD_RSA_SCERET_KEY_MAP);//
		
		Map<String ,String> JD_RSA_PUBLIC_KEY_MAP = new HashMap<>();
		JD_RSA_PUBLIC_KEY_MAP.put("CONTENT", JD_RSA_PUBLIC_KEY);
		JD_RSA_PUBLIC_KEY_MAP.put("ID", JD_RSA_PUBLIC_KEY_ID);
		JD_Prams.put(JDPayExtEnum.JD_RSA_PUBLIC_KEY.name(), JD_RSA_PUBLIC_KEY_MAP);
		
//		/JD_CLUB_NUMBER_CARD_ID ,JD_DES_SCERET_KEY , JD_MD5_SCERET_KEY,JD_SELLER_ID,JD_RSA_SCERET_KEY
		
		

		rpUserPayConfigService.updateUserPayConfig(rpUserPayConfig.getUserNo(), productCode, productName, 
				rpUserPayConfig.getRiskDay(), rpUserPayConfig.getFundIntoType(), rpUserPayConfig.getIsAutoSett(),
				we_appId, we_merchantId, we_partnerKey, ali_partner, ali_sellerId, ali_key, ali_appid, ali_rsaPrivateKey, 
				ali_rsaPublicKey , securityRating , merchantServerIp,JD_SELLER_ID,
				JD_Prams);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ： 删除
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:delete")
	@RequestMapping(value = "/delete", method ={RequestMethod.POST, RequestMethod.GET})
	public String delete(Model model, DwzAjax dwz, @RequestParam("userNo") String userNo) {
		rpUserPayConfigService.deleteUserPayConfig(userNo);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ： 审核
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:add")
	@RequestMapping(value = "/audit", method ={RequestMethod.POST, RequestMethod.GET})
	public String audit(Model model, DwzAjax dwz, @RequestParam("userNo") String userNo
			, @RequestParam("auditStatus") String auditStatus) {
		rpUserPayConfigService.audit(userNo, auditStatus);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ：跳转编辑银行卡信息
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:edit")
	@RequestMapping(value = "/editBankUI", method = RequestMethod.GET)
	public String editBankUI(Model model, @RequestParam("userNo") String userNo) {
		RpUserBankAccount rpUserBankAccount = rpUserBankAccountService.getByUserNo(userNo);
		RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(userNo);
		model.addAttribute("BankCodeEnums", BankCodeEnum.toList());
		model.addAttribute("BankAccountTypeEnums", BankAccountTypeEnum.toList());
		model.addAttribute("CardTypeEnums", CardTypeEnum.toList());
		model.addAttribute("rpUserBankAccount", rpUserBankAccount);
		model.addAttribute("rpUserInfo", rpUserInfo);
		return "pay/config/editBank";
	}
	
	/**
	 * 函数功能说明 ： 更新银行卡信息
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:config:edit")
	@RequestMapping(value = "/editBank", method = RequestMethod.POST)
	public String editBank(Model model, RpUserBankAccount rpUserBankAccount, DwzAjax dwz) {
		rpUserBankAccountService.createOrUpdate(rpUserBankAccount);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
}
