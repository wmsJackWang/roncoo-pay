package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;


/*
 * 微信公众号登入
 */

@Controller
@RequestMapping("/wx")
public class WxOfficialAccountLoginController {
	
	@Autowired
	private WxMpService wxMpService;
	

	@ResponseBody
	@RequestMapping(value = "/testTicket")//openIdDomain
	public String test() {
		try {
			WxMpConfigStorageHolder.set("wx21eb36134e421873");//第一个公众号
			WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(1);
		    Assert.assertNotNull(ticket.getUrl());
		    Assert.assertNotNull(ticket.getTicket());
		    Assert.assertTrue(ticket.getExpireSeconds() == -1);
		    System.out.println(ticket);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/*
	 * 作为开放平台，需要有安全合理的商户管理平台。appid只是暂时放在这.
	 */
	@RequestMapping("/getLastQrcodeUrl")
	public String getLastQrcodeUrl(@RequestParam String appid) {
		String url = null;
		try {
			WxMpConfigStorageHolder.set("wx21eb36134e421873");//第一个公众号
			WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(1);
		    Assert.assertNotNull(ticket.getUrl());
		    Assert.assertNotNull(ticket.getTicket());
		    Assert.assertTrue(ticket.getExpireSeconds() == -1);
		    System.out.println(ticket);
		    url = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
		    Assert.assertNotNull(url);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
		
	}
	
	@RequestMapping("/getTempQrcodeUrl")
	public String getTempQrcodeUrl(@RequestParam String appid) {
		String url = null;
		try {
			WxMpConfigStorageHolder.set("wx21eb36134e421873");//第一个公众号
			WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 100000);
		    Assert.assertNotNull(ticket.getUrl());
		    Assert.assertNotNull(ticket.getTicket());
		    Assert.assertTrue(ticket.getExpireSeconds() == -1);
		    System.out.println(ticket);
		    url = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
		    Assert.assertNotNull(url);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
		
	}
	
	
	
	
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
//    }
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
