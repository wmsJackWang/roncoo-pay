package com.sohu.modules.redpacket.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 service_node_info
 * 
 * @author ruoyi
 * @date 2021-03-05
 */
public class ServiceNodeInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 记录id */
    private Long id;

    /** 服务名 */ 
    private String servicename;

    /** 节点id */ 
    private Long nodeId;

    /** 创建时间 */ 
    private Date createdOn;

    /** 刷新时间 */ 
    private Date updatedOn;

    /** $column.columnComment */ 
    private Long versionOptimizedLock;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setServicename(String servicename) 
    {
        this.servicename = servicename;
    }

    public String getServicename() 
    {
        return servicename;
    }
    public void setNodeId(Long nodeId) 
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId() 
    {
        return nodeId;
    }
    public void setCreatedOn(Date createdOn) 
    {
        this.createdOn = createdOn;
    }

    public Date getCreatedOn() 
    {
        return createdOn;
    }
    public void setUpdatedOn(Date updatedOn) 
    {
        this.updatedOn = updatedOn;
    }

    public Date getUpdatedOn() 
    {
        return updatedOn;
    }
    public void setVersionOptimizedLock(Long versionOptimizedLock) 
    {
        this.versionOptimizedLock = versionOptimizedLock;
    }

    public Long getVersionOptimizedLock() 
    {
        return versionOptimizedLock;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("servicename", getServicename())
            .append("nodeId", getNodeId())
            .append("createdOn", getCreatedOn())
            .append("updatedOn", getUpdatedOn())
            .append("versionOptimizedLock", getVersionOptimizedLock())
            .toString();
    }
}
