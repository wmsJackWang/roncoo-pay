package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.RetMessages;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-08
 */
@Mapper
public interface RetMessagesMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public RetMessages selectRetMessagesById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param retMessages 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<RetMessages> selectRetMessagesList(RetMessages retMessages);

    /**
     * 新增【请填写功能名称】
     * 
     * @param retMessages 【请填写功能名称】
     * @return 结果
     */
    public int insertRetMessages(RetMessages retMessages);

    /**
     * 修改【请填写功能名称】
     * 
     * @param retMessages 【请填写功能名称】
     * @return 结果
     */
    public int updateRetMessages(RetMessages retMessages);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteRetMessagesById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRetMessagesByIds(String[] ids);
}
