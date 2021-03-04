package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.dao.MdUnlockArticleMapper;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.MdUnlockArticle;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IMdUnlockArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.convert.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
@Service
public class MdUnlockArticleServiceImpl implements IMdUnlockArticleService 
{
    @Autowired
    private MdUnlockArticleMapper mdUnlockArticleMapper;
    
    @Override
    public boolean isExist(Long articleid , Long appid) {
    	
    	MdUnlockArticle mdUnlockArticle = new MdUnlockArticle();
    	
    	mdUnlockArticle.setArticleId(articleid);
    	mdUnlockArticle.setAppid(appid);
    	
    	MdUnlockArticle result = mdUnlockArticleMapper.selectMdUnlockArticle(mdUnlockArticle);
    	
    	return result ==null? false:true;
    }
    

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public MdUnlockArticle selectMdUnlockArticleById(Long id)
    {
        return mdUnlockArticleMapper.selectMdUnlockArticleById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<MdUnlockArticle> selectMdUnlockArticleList(MdUnlockArticle mdUnlockArticle)
    {
        return mdUnlockArticleMapper.selectMdUnlockArticleList(mdUnlockArticle);
    }
    
    
    
    /**
     * 查询【请填写功能名称】列表
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public MdUnlockArticle selectMdUnlockArticle(MdUnlockArticle mdUnlockArticle)
    {
        return mdUnlockArticleMapper.selectMdUnlockArticle(mdUnlockArticle);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertMdUnlockArticle(MdUnlockArticle mdUnlockArticle)
    {
        return mdUnlockArticleMapper.insertMdUnlockArticle(mdUnlockArticle);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateMdUnlockArticle(MdUnlockArticle mdUnlockArticle)
    {
        mdUnlockArticle.setUpdateTime(new Date());
        return mdUnlockArticleMapper.updateMdUnlockArticle(mdUnlockArticle);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMdUnlockArticleByIds(String ids)
    {
        return mdUnlockArticleMapper.deleteMdUnlockArticleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteMdUnlockArticleById(Long id)
    {
        return mdUnlockArticleMapper.deleteMdUnlockArticleById(id);
    }
}
