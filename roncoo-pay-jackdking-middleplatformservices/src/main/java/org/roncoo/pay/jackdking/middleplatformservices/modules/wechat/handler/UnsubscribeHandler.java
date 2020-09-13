package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {
	

	@Autowired
	IWeixinService iWeixinService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // TODO 可以更新本地数据库为取消关注状态
        
        Weixin newUser = new Weixin();
        newUser.setOpenid(openId);
        

        List<Weixin> result = iWeixinService.selectWeixinList(newUser);
        
        
        if(!ObjectUtils.isEmpty(result)) {
        	
        	Weixin updateUser = result.get(0);
        	updateUser.setStatus(1);
//        	updateUser.setToken(wxMessage.getContent());
        	iWeixinService.updateWeixin(updateUser);//设置为不可用
        	
        }
        
        
        return null;
    }

}
