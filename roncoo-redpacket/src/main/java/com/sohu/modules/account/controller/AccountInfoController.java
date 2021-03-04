package com.sohu.modules.account.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sohu.modules.account.entity.AccountInfoEntity;
import com.sohu.modules.account.service.IAccountInfoService;
import com.sohu.common.utils.PageUtils;
import com.sohu.common.utils.Query;
import com.sohu.common.utils.R;




/**
 * ${comments}
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-24 14:05:43
 */
@RestController
@RequestMapping("account/accountinfo")
public class AccountInfoController {
	@Autowired
	private IAccountInfoService accountInfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<AccountInfoEntity> accountInfoList = accountInfoService.queryList(query);
		int total = accountInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(accountInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(AccountInfoEntity accountInfo){
		accountInfoService.save(accountInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(AccountInfoEntity accountInfo){
		accountInfoService.update(accountInfo);
		
		return R.ok();
	}
	
	
}
