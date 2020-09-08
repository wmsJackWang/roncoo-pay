package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.roncoo.pay.jackdking.middleplatformservices.utils.SystemConfigConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;


/*
 * 微信公众号登入
 */

@Controller
@RequestMapping("/wx")
public class WxOfficialAccountLoginController {
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(WxOfficialAccountLoginController.class);
//	
//	@Autowired
//	private SystemConfigConst configConst;
//	
//    @RequestMapping("/getQrcode")
//    public @ResponseBody String getQrcode(@RequestParam(value = "sceneId")int sceneId) throws Exception{
//    
//        
//        String ticket = createTempTicket(configConst.getAccessToken(),"2592000",sceneId);
//        LOGGER.info("get wechat qrcode  ==> start");
//        LOGGER.info("sceneId :"+sceneId);
//        LOGGER.info("ticket :"+ticket);
//        LOGGER.info("get wechat qrcode  ==> end");
//        return ticket;
//    }-*
//    /** 
//     * 创建临时带参数二维码 
//     * @param accessToken 
//     * @expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。 
//     * @param sceneId 场景Id 
//     * @return 
//     */  
//    public String createTempTicket(String accessToken, String expireSeconds, int sceneId) {  
//      
//        TreeMap<String,String> params = new TreeMap<String,String>();  
//        params.put("access_token", accessToken);  
//        Map<String,Integer> intMap = new HashMap<String,Integer>();
//        intMap.put("scene_id",sceneId);  
//        Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();  
//        mapMap.put("scene", intMap);  
//        
//        Map<String,Object> paramsMap = new HashMap<String,Object>();  
//        paramsMap.put("expire_seconds", expireSeconds);  
//        paramsMap.put("action_name", SystemConfigConst.QR_SCENE);  
//        paramsMap.put("action_info", mapMap);  
//        String data = new Gson().toJson(paramsMap);  
////        String tse = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD,SystemConfigConst.create_ticket_path,params,data);  
//        
//        JSONObject jsonObject = JSON.parseObject("");
//        LOGGER.info("ticket :"+jsonObject.getString("ticket"));
//
//        return SystemConfigConst.showqrcode_path+"?ticket="+jsonObject.getString("ticket");
// }
//	
}
