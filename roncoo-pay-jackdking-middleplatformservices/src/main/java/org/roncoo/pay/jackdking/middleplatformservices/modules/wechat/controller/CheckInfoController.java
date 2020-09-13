package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.util.List;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/wx/check")
public class CheckInfoController {
	
	@Autowired
	private IWeixinService  iWeixinService;
	
	//验证是否获得 文章查看权限
	@ResponseBody
	@RequestMapping(value = "/isSubscribe", method = RequestMethod.POST)//openIdDomain
	public String checkIsLocked(@RequestBody String token) {

		JSONObject returnMsg = new JSONObject();
		
		Weixin weixin = new Weixin();
		weixin.setToken(token);
		List<Weixin>  result  =  iWeixinService.selectWeixinList(weixin);
		
		returnMsg.put("locked", true);//默认是锁住的
		
		if(!ObjectUtils.isEmpty(result)&&result.get(0).getStatus()!=null&&result.get(0).getStatus()==0)
			returnMsg.put("locked", false);
		
		return returnMsg.toJSONString();
	}

}
