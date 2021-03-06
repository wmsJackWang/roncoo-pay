package com.sohu.modules.redpacket.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohu.common.baseenum.BaseEnum.CheckParameterExceptionEnum;
import com.sohu.common.utils.CheckSignUtil;
import com.sohu.common.utils.R;
import com.sohu.common.validator.Assert;
import com.sohu.modules.redpacket.dao.RedpacketInfoDao;
import com.sohu.modules.redpacket.entity.RedpacketInfoEntity;
import com.sohu.modules.redpacket.service.IRedpacketInfoService;



@Service("redpacketInfoService")
public class RedpacketInfoServiceImpl implements IRedpacketInfoService {
	@Autowired
	private RedpacketInfoDao redpacketInfoDao;
	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public RedpacketInfoEntity queryObject(Long id){
		return redpacketInfoDao.queryObject(id);
	}
	
	@Override
	public List<RedpacketInfoEntity> queryList(Map<String, Object> map){
		return redpacketInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return redpacketInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(RedpacketInfoEntity redpacketInfo){
		redpacketInfoDao.insert(redpacketInfo);
	}
	
	@Override
	public void update(RedpacketInfoEntity redpacketInfo){
		redpacketInfoDao.update(redpacketInfo);
	}
	
	@Override
	public void delete(Long id){
		redpacketInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		redpacketInfoDao.deleteBatch(ids);
	}

	@Override
//	@Transactional
//	问题的原因在于早期项目中只有一个数据源，也只有一个与该数据源绑定的事务管理器，因此直接使用@Transactional()注释时Spring会帮助我们自动识别出来，但是后期当项目中同时出现两个数据源和两个相关的事务管理器时，在使用@Transactional()注解时如果不直接指定，Spring就不知道具体使用哪一个事务管理器来进行事务管理了，因此需要通过某种方式来具体指定一下。
//
//	指定的方式可以通过使用 @Transactional(transactionManager = “aaaTransactionManager”) 来进行指定
	
	public R createRedPacket(RedpacketInfoEntity redpacketInfo,Map<String, String[]> parameterMap) {
		// TODO Auto-generated method stub
		Assert.isTrue(redpacketInfo.getAmt().compareTo(BigDecimal.ZERO)<=0, "红包金额必须大于零元");
//		String seed = "";//从缓存中拿到seed值，加钱秘钥值
//		String signOrder = CheckSignUtil.signOrderArr(parameterMap,seed);
//		Assert.isFalse(signOrder.equals(redpacketInfo.getSign()),CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(), "签名错误");
//		
		
		
		//插入发红包订单数据
		redpacketInfo.setPsTransId("RP"+System.currentTimeMillis());
		redpacketInfoDao.insert(redpacketInfo);
		
		
		
		
		return R.ok();
	}
	
}
