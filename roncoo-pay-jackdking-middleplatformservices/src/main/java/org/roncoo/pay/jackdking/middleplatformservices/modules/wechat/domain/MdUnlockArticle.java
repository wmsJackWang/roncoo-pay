package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 md_unlock_article
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
public class MdUnlockArticle 
{
	
	public MdUnlockArticle() {
		this.createTime = new Date();
		this.updateTime = new Date();
		
	}
	
	
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 文章ID */
    private Long articleId;

    /** 文章解锁次数，以此来计算受欢迎情况 */
    private Integer unlockCount;

    /** 博客网站应用id */
    private Long appid;
    
    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setArticleId(Long articleId) 
    {
        this.articleId = articleId;
    }

    public Long getArticleId() 
    {
        return articleId;
    }
    public void setUnlockCount(Integer unlockCount) 
    {
        this.unlockCount = unlockCount;
    }

    public Integer getUnlockCount() 
    {
        return unlockCount;
    }
    public void setAppid(Long appid) 
    {
        this.appid = appid;
    }

    public Long getAppid() 
    {
        return appid;
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("articleId", getArticleId())
            .append("unlockCount", getUnlockCount())
            .append("appid", getAppid())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
