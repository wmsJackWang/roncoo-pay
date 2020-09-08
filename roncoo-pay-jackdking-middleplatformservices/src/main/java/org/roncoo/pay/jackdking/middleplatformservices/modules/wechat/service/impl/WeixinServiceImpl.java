package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.impl;

import java.util.List;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.dao.WeixinMapper;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.convert.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-08
 */
@Service
public class WeixinServiceImpl implements IWeixinService 
{
    @Autowired
    private WeixinMapper weixinMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Weixin selectWeixinById(Long id)
    {
        return weixinMapper.selectWeixinById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param weixin 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Weixin> selectWeixinList(Weixin weixin)
    {
        return weixinMapper.selectWeixinList(weixin);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param weixin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertWeixin(Weixin weixin)
    {
        return weixinMapper.insertWeixin(weixin);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param weixin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateWeixin(Weixin weixin)
    {
        return weixinMapper.updateWeixin(weixin);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWeixinByIds(String ids)
    {
        return weixinMapper.deleteWeixinByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteWeixinById(Long id)
    {
        return weixinMapper.deleteWeixinById(id);
    }
}
