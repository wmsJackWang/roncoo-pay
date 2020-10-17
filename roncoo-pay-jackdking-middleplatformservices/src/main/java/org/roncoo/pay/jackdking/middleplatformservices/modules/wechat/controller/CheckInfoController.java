package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.HttpHelper;
import com.roncoo.pay.common.core.utils.StringUtil;

@Controller
@RequestMapping("/wx/check")
public class CheckInfoController {
	
	private static final Logger log = LoggerFactory.getLogger(CheckInfoController.class);

	
	@Autowired
	private IWeixinService  iWeixinService;
	
	//验证是否获得 文章查看权限
	@ResponseBody
	@RequestMapping(value = "/isSubscribe", method = RequestMethod.POST)//openIdDomain
	public String checkIsLocked(@RequestParam String token , HttpServletResponse response) {
		
		// 允许跨域访问的域名：若有端口需写全（协议+域名+端口），若没有端口末尾不用加'/'
		response.setHeader("Access-Control-Allow-Origin", "*"); 

		JSONObject returnMsg = new JSONObject();
		
		Weixin weixin = new Weixin();
		weixin.setToken(token);
		List<Weixin>  result  =  iWeixinService.selectWeixinList(weixin);
		
		returnMsg.put("locked", true);//默认是锁住的
		
		//查看用户是否已经解锁文章
		if(!ObjectUtils.isEmpty(result)&&result.get(0).getStatus()!=null&&result.get(0).getStatus()==0)
			returnMsg.put("locked", false);
		
		//查看博客网站所有文章 锁定参数是否为true，为true则锁定未解锁的用户，false则所有文章不锁定

		JSONObject params = new JSONObject();
		params.put("configKey", "locked");
		String url = "http://bittechblog.com/api/system/config/getSysConfig";
		
		
		String resp = null;
		HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(),String.valueOf(entry.getValue())));
            }
            post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
            HttpResponse httpResponse = httpClient.execute(post);
            resp = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
//		String resp = HttpHelper.post(params, url);
		log.info("查询网站参数locked的返回结果：{}",resp);
		
		
		//网站默认是锁住的，但是如果网站参数是false，则所有文章都不锁。
		if(!StringUtil.isEmpty(resp)&&"false".equalsIgnoreCase(JSON.parseObject(resp).getString("configValue")))
			returnMsg.put("locked", false);
		
		return returnMsg.toJSONString();
	}

}
