package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.handler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.roncoo.pay.jackdking.middleplatformservices.consts.RedisConstant;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.RetMessages;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IRetMessagesService;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {
	
	@Autowired
	@Qualifier("rediTemplateA")
	private RedisTemplate redistemplate;

	@Autowired
	IWeixinService iWeixinService;
	
	@Autowired
	IRetMessagesService iRetMessagesService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
            //网站文章解锁 token
            if(isAlphaNumeric(wxMessage.getContent())&&wxMessage.getContent().length()==6)
            {
            	
            	 Weixin newUser = new Weixin();
                 newUser.setOpenid(wxMessage.getFromUser());
                 
                 //用户解锁  发送得token，存放到redis，用户统计  热门吸引用户关注文章，以帮助创作者得知技术热点。
                 redistemplate.opsForValue().set(RedisConstant.USER_SUBS_TOKEN_PREFIX+wxMessage.getContent(), wxMessage.getContent());

                 List<Weixin> result = iWeixinService.selectWeixinList(newUser);

                 //用户在粉丝列表中 ， 如果用户是取关后又关注  那就更新
                 if(!ObjectUtils.isEmpty(result)) {
                 	
                 	Weixin updateUser = result.get(0);
                 	updateUser.setStatus(0);
                 	updateUser.setToken(wxMessage.getContent());//发送新的token会覆盖掉之前的token值
                 	iWeixinService.updateWeixin(updateUser);//设置为不可用
                 	
                 //用户不在粉丝列表中，之前没关注过这个号
                 }else{
                     
                  	logger.info("新关注用户{},不在粉丝列表中",wxMessage.getFromUser());
                 	newUser.setToken(wxMessage.getContent());
                 	newUser.setStatus(0);////已关注的用户发送token 默认解锁
                 	iWeixinService.insertWeixin(newUser);
                 }
            	
            	String content = "恭喜已经解锁网站全部文章！！";
            	WxMpXmlOutMessage message = new TextBuilder().content(content)
						 .toUser(wxMessage.getFromUser())
						 .fromUser(wxMessage.getToUser())
						 .build();

            	return message;//("感谢关注", wxMessage, weixinService);
            	
            }
            
            //其他消息 回复
            RetMessages param = new RetMessages();
            param.setMsg(wxMessage.getContent());
            RetMessages messages = iRetMessagesService.selectOne(param);
            
            
            if(messages==null) {
            	
            }else {
            	String content = messages.getRetmsg();
            	WxMpXmlOutMessage message = new TextBuilder().content(content)
						 .toUser(wxMessage.getFromUser())
						 .fromUser(wxMessage.getToUser())
						 .build();

            	return message;//("感谢关注", wxMessage, weixinService);
            }
            
            
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        
///
        
//        DROP TABLE IF EXISTS `weixin`;
//        CREATE TABLE `weixin` (
//          `id` int(11) NOT NULL AUTO_INCREMENT,
//          `openid` varchar(255) NOT NULL,
//          `token` varchar(255) NOT NULL,
//			`status`  int(2) NOT NULL,      
//          PRIMARY KEY (`id`)
//        ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
        
//      CREATE TABLE `ret_messages` (
//      `id` int(11) NOT NULL AUTO_INCREMENT,
//      `msg` varchar(255) NOT NULL,
//      `retmsg` varchar(255) NOT NULL,  
//      PRIMARY KEY (`id`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
    
        
        
        //TODO 组装回复消息  默认方式
//        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);


        
        String content = "你好，欢迎关注FrontierTechnology！   \n\n公众号专栏列表 : \n<a href='https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI5NDcxNDY2Ng==&action=getalbum&album_id=1363971746453585923&scene=173#wechat_redirect&scene=0&subscene=92&sessionid=1599487747&enterid=1599487754'>Java架构师方案宝典</a>\n<a href='https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI5NDcxNDY2Ng==&action=getalbum&album_id=1370184404953530368&scene=173#wechat_redirect&scene=0&subscene=92&sessionid=1599488643&enterid=1599488854'>你或许感兴趣的科技美文</a>"
        		+ "\n\n头条搜索\"<a href='https://profile.zjurl.cn/rogue/ugc/profile/?version_code=7.5.7&version_name=70507&user_id=2436116630212455&media_id=1662199072727051&request_source=1&active_tab=dongtai&device_id=65&app_name=news_article'>前沿科技bot</a>\",这里有最新的科技资讯报道,期待您的关注"
        		+ "\n\n<a href='http://bittechblog.com/blog/'>本公众号技术博客网站</a>：http://bittechblog.com/blog/";

        return new org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.build.TextBuilder().build(content, wxMessage, weixinService);

    }
    
    
    /**
     * 方法功能：判断一个字符串是否有字母和数字组成
     * @param String s
     * @return boolean
     * */
    public static boolean isAlphaNumeric(String s){
      Pattern p = Pattern.compile("[0-9a-zA-Z]{1,}");
      Matcher m = p.matcher(s);
      return m.matches();
    }
    

}
