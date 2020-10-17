package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.impl;

import java.util.List;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.dao.RetMessagesMapper;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.RetMessages;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IRetMessagesService;
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
public class RetMessagesServiceImpl implements IRetMessagesService 
{
    @Autowired
    private RetMessagesMapper retMessagesMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public RetMessages selectRetMessagesById(Long id)
    {
        return retMessagesMapper.selectRetMessagesById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param retMessages 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<RetMessages> selectRetMessagesList(RetMessages retMessages)
    {
        return retMessagesMapper.selectRetMessagesList(retMessages);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param retMessages 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertRetMessages(RetMessages retMessages)
    {
        return retMessagesMapper.insertRetMessages(retMessages);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param retMessages 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRetMessages(RetMessages retMessages)
    {
        return retMessagesMapper.updateRetMessages(retMessages);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRetMessagesByIds(String ids)
    {
        return retMessagesMapper.deleteRetMessagesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteRetMessagesById(Long id)
    {
        return retMessagesMapper.deleteRetMessagesById(id);
    }

	@Override
	public RetMessages selectOne(RetMessages retMessages) {
		// TODO Auto-generated method stub
		return retMessagesMapper.selectRetMessagesOne(retMessages);
	}
}
