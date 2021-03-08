package com.sohu.modules.redpacket.service;

import java.util.List;

import com.sohu.modules.redpacket.entity.ServiceNodeInfo;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2021-03-05
 */
public interface IServiceNodeInfoService 
{
	
	/*
	 * 服务的信条检测
	 */
	public int checkBeat(ServiceNodeInfo serviceNodeInfo);
	
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ServiceNodeInfo selectServiceNodeInfoById(Long id);

    /*
     * 根据服务名和服务id来查询唯一的servicenode记录
     */
    public ServiceNodeInfo selectServiceNodeInfoByNameAndNodeid(ServiceNodeInfo serviceNodeInfo);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param serviceNodeInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ServiceNodeInfo> selectServiceNodeInfoList(ServiceNodeInfo serviceNodeInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param serviceNodeInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertServiceNodeInfo(ServiceNodeInfo serviceNodeInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param serviceNodeInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateServiceNodeInfo(ServiceNodeInfo serviceNodeInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteServiceNodeInfoByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteServiceNodeInfoById(Long id);

	public void getNodeId() throws Exception;
}
