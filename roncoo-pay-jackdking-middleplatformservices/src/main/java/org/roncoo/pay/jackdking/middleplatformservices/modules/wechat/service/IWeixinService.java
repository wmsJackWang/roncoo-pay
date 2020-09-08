package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service;

import java.util.List;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.Weixin;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2020-09-08
 */
public interface IWeixinService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Weixin selectWeixinById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param weixin 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Weixin> selectWeixinList(Weixin weixin);

    /**
     * 新增【请填写功能名称】
     * 
     * @param weixin 【请填写功能名称】
     * @return 结果
     */
    public int insertWeixin(Weixin weixin);

    /**
     * 修改【请填写功能名称】
     * 
     * @param weixin 【请填写功能名称】
     * @return 结果
     */
    public int updateWeixin(Weixin weixin);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWeixinByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteWeixinById(Long id);
}
