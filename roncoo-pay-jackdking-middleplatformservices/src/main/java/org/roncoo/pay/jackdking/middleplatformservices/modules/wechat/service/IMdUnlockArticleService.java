package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service;

import java.util.List;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.MdUnlockArticle;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
public interface IMdUnlockArticleService 
{
	
	
	public boolean isExist(Long articleid , Long appid) ;
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public MdUnlockArticle selectMdUnlockArticleById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<MdUnlockArticle> selectMdUnlockArticleList(MdUnlockArticle mdUnlockArticle);

    /**
     * 查询【请填写功能名称】
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    public MdUnlockArticle selectMdUnlockArticle(MdUnlockArticle mdUnlockArticle);
    
    /**
     * 新增【请填写功能名称】
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 结果
     */
    public int insertMdUnlockArticle(MdUnlockArticle mdUnlockArticle);

    /**
     * 修改【请填写功能名称】
     * 
     * @param mdUnlockArticle 【请填写功能名称】
     * @return 结果
     */
    public int updateMdUnlockArticle(MdUnlockArticle mdUnlockArticle);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMdUnlockArticleByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteMdUnlockArticleById(Long id);
}
