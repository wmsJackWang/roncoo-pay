package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WxDomainRightController {
	
	private static Map<String,String> sizemap = new HashMap<String, String>();
	static {
		sizemap.put("baidu_verify_code-ehN9lzWbui.html", "2ca8182a472f28ec6598b29f501c0e5f");
		sizemap.put("baidu_verify_code-L413BKVJUv.html", "299be29684e71b56d8a5fe0bc7e8082e");
		sizemap.put("sogousiteverification.txt", "nkFsnNbJWt");//搜狗平台
		
	}

	//百度站长平台验证
	@ResponseBody
	@RequestMapping("/baiduVerify/{filename:[_a-zA-Z0-9\\-\\.]+}")//openIdDomain
	public String baiduVerify(@PathVariable("filename") String filename) {

		//百度站长平台验证
		return sizemap.get(filename);
	}
	
	//验证获取openid的接口域名权限
	@ResponseBody
	@RequestMapping("/openIdDomain/{filename:[_a-zA-Z0-9\\.]+}")//openIdDomain
	public String openIdCallBackDomain(@PathVariable("filename") String filename) {

		
		return "mVNLTSwlCYsWLaKb";
	}
}
