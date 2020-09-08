package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 ret_messages
 * 
 * @author ruoyi
 * @date 2020-09-08
 */
public class RetMessages
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private String msg;

    /** $column.columnComment */
    private String retmsg;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMsg(String msg) 
    {
        this.msg = msg;
    }

    public String getMsg() 
    {
        return msg;
    }
    public void setRetmsg(String retmsg) 
    {
        this.retmsg = retmsg;
    }

    public String getRetmsg() 
    {
        return retmsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("msg", getMsg())
            .append("retmsg", getRetmsg())
            .toString();
    }
}
