package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.handler;

import java.util.List;
import java.util.Map;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	
	@Autowired
	IWeixinService iWeixinService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // TODO 可以添加关注用户到本地数据库
            }
            
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }

        Weixin newUser = new Weixin();
        newUser.setOpenid(wxMessage.getFromUser());
        
        List<Weixin> result = iWeixinService.selectWeixinList(newUser);
        
        if(ObjectUtils.isEmpty(result))
        {
        	logger.info("新关注用户{},不在粉丝列表中",wxMessage.getFromUser());
        	newUser.setToken(wxMessage.getContent());
        	newUser.setStatus(1);
        	iWeixinService.insertWeixin(newUser);
        }
        else {
        	logger.info("新关注用户{},在粉丝列表中",wxMessage.getFromUser());
        	Weixin updateUser = result.get(0);
//        	updateUser.setStatus(1);
        	updateUser.setToken(wxMessage.getContent());
        	iWeixinService.updateWeixin(updateUser);
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }
        
        String content = "欢迎关注 前沿科技   \n专栏列表 : \n <a href='https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI5NDcxNDY2Ng==&action=getalbum&album_id=1363971746453585923&scene=173#wechat_redirect&scene=0&subscene=92&sessionid=1599487747&enterid=1599487754'>Java架构师方案宝典</a>\n<a https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI5NDcxNDY2Ng==&action=getalbum&album_id=1370184404953530368&scene=173#wechat_redirect&scene=0&subscene=92&sessionid=1599488643&enterid=1599488854'>你或许感兴趣的</a>";

        try {
        	WxMpXmlOutMessage message = new TextBuilder().content(content)
        												 .toUser(wxMessage.getFromUser())
        												 .fromUser(wxMessage.getToUser())
        												 .build();
        	
            return message;//("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        //TODO
        return null;
    }

}
