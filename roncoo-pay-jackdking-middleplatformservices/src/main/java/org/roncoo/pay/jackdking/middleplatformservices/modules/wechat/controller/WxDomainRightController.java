package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WxDomainRightController {

	
	
	//验证获取openid的接口域名权限
	@ResponseBody
	@RequestMapping("/openIdDomain/{filename:[_a-zA-Z0-9\\.]+}")//openIdDomain
	public String openIdCallBackDomain(@PathVariable("filename") String filename) {

		
		return "mVNLTSwlCYsWLaKb";
	}
}
