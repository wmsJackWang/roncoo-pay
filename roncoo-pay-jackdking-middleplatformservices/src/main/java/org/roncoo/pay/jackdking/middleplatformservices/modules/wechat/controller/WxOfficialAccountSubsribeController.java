package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

public class WxOfficialAccountSubsribeController {
	
	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}

	/**
	 * 获取request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	

	@RequestMapping("/locked")
	@ResponseBody
	public JSONObject index(HttpServletRequest request, HttpServletResponse response) {
	    // 跨域
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		JSONObject result = new JSONObject();
//	    String token = getString("token");
//	    String openid = service.findByToken(token);
//	    if (openid == null || "".equals(openid)) {
//	    	result.put("locked", true);
//	    } else {
//	    	result.put("locked", false);
//	    }
		return null;
	}
	
//	
//	protected void processInTextMsg(InTextMsg inTextMsg) {
//	    String msgContent = inTextMsg.getContent().trim();
//
//	    if ("2048".equals(msgContent)) {
//
//	    } else if (msgContent.length() == 6) {
//	        Weixin param = new Weixin();
//	        param.setOpenid(inTextMsg.getFromUserName());
//	        param.setToken(msgContent);
//	        param.save();
//
//	        OutTextMsg outMsg = new OutTextMsg(inTextMsg);
//	        outMsg.setContent("恭喜您已经解锁博客全部文章~");
//	        render(outMsg);
//	    } else {
//	        renderDefault();
//	    }
//	}

}
