package com.sohu.modules.redpacket.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sohu.modules.redpacket.entity.RedpacketInfoEntity;
import com.sohu.modules.redpacket.service.IRedpacketInfoService;
import com.sohu.common.utils.PageUtils;
import com.sohu.common.utils.Query;
import com.sohu.common.utils.R;




/**
 * 发红包记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
@RestController
@RequestMapping("/")
public class RedpacketInfoController {
	@Autowired
	private IRedpacketInfoService redpacketInfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RedpacketInfoEntity> redpacketInfoList = redpacketInfoService.queryList(query);
		int total = redpacketInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(redpacketInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(RedpacketInfoEntity redpacketInfo){
		redpacketInfoService.save(redpacketInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(RedpacketInfoEntity redpacketInfo){
		redpacketInfoService.update(redpacketInfo);
		
		return R.ok();
	}

	@RequestMapping("/createRedPacket")
	public R createRedPacket(RedpacketInfoEntity redpacketInfo, HttpServletRequest request) {

		
		Map<String, String[]> parameterMap = request.getParameterMap();
		return redpacketInfoService.createRedPacket(redpacketInfo, parameterMap);
	}
	
	
}
