package com.sohu.modules.redpacket.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sohu.modules.redpacket.dao.ServiceNodeInfoMapper;
import com.sohu.modules.redpacket.entity.ServiceNodeInfo;
import com.sohu.modules.redpacket.service.IServiceNodeInfoService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-05
 */
@Service("ServiceNodeInfoServiceImpl")
public class ServiceNodeInfoServiceImpl implements IServiceNodeInfoService 
{
    @Autowired
    private ServiceNodeInfoMapper serviceNodeInfoMapper;
    
	
	private static String PAY_ORDER_SERVICE="PayOrderService";
	
	private static ServiceNodeInfo serviceNodeInfo = new ServiceNodeInfo();
	
	private static int NodesTotal = 10;
	
	private static boolean serviceReady = false;
	
	
//	@Scheduled(cron = "5/1 * * * * *")
//	@Scheduled(fixedRate =10*1000)
	public void heartBeatCheck() {
		if(serviceReady) {
			ServiceNodeInfo result = selectServiceNodeInfoByNameAndNodeid(serviceNodeInfo);
			serviceNodeInfoMapper.checkBeat(result);
			System.out.println("heartBeatCheck");
		}
	}

    

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ServiceNodeInfo selectServiceNodeInfoById(Long id)
    {
        return serviceNodeInfoMapper.selectServiceNodeInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param serviceNodeInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ServiceNodeInfo> selectServiceNodeInfoList(ServiceNodeInfo serviceNodeInfo)
    {
        return serviceNodeInfoMapper.selectServiceNodeInfoList(serviceNodeInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param serviceNodeInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertServiceNodeInfo(ServiceNodeInfo serviceNodeInfo)
    {
        return serviceNodeInfoMapper.insertServiceNodeInfo(serviceNodeInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param serviceNodeInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateServiceNodeInfo(ServiceNodeInfo serviceNodeInfo)
    {
        return serviceNodeInfoMapper.updateServiceNodeInfo(serviceNodeInfo);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceNodeInfoByIds(String ids)
    {
        return serviceNodeInfoMapper.deleteServiceNodeInfoByIds((ids!=null&&!ids.isEmpty())?null:ids.split(","));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteServiceNodeInfoById(Long id)
    {
        return serviceNodeInfoMapper.deleteServiceNodeInfoById(id);
    }

	@Override
	public ServiceNodeInfo selectServiceNodeInfoByNameAndNodeid(ServiceNodeInfo serviceNodeInfo) {
		// TODO Auto-generated method stub
		return serviceNodeInfoMapper.selectServiceNodeInfoByNameAndNodeid(serviceNodeInfo);
	}

	@Override
	public int checkBeat(ServiceNodeInfo serviceNodeInfo) {
		// TODO Auto-generated method stub
		return serviceNodeInfoMapper.checkBeat(serviceNodeInfo);
	}



	public void getNodeId() throws Exception {
		
		System.out.println("获取服务id");
		ServiceNodeInfo nodeInfo = new ServiceNodeInfo();
		nodeInfo.setServicename(PAY_ORDER_SERVICE);
		nodeInfo.setNodeId((long)getRandom(NodesTotal));
		ServiceNodeInfo result = selectServiceNodeInfoByNameAndNodeid(nodeInfo);
		
		//获得当前时间和当前时间前30秒时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = new GregorianCalendar();
		Date date = new Date();
		System.out.println("系统当前时间      ："+df.format(date));
		c.setTime(date);//设置参数时间
		c.add(Calendar.SECOND,-30);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
		date=c.getTime(); //这个时间就是日期往后推一天的结果
		String str = df.format(date);
		System.out.println("系统前30秒时间："+str); 

		System.out.println("服务节点信息result："+result.toString()); 
		if(result.getUpdatedOn().compareTo(date)<0)//节点信息最近刷新的时间 比当前时间早30s以上,说明节点信息已经被服务释放了
		{
			if(1!=checkBeat(result))
				throw new Exception("注册服务id失败");
			serviceReady = true;
			serviceNodeInfo.setNodeId(result.getNodeId());
			serviceNodeInfo.setServicename(PAY_ORDER_SERVICE);
		}
		
		
	}
	
	
	private static int  getRandom (int n) {
		return new Random().nextInt(n);
	}

}
