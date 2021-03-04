package com.sohu.common.utils;

import org.apache.commons.lang.StringUtils;

import com.sohu.common.xss.SQLFilter;
import com.sohu.common.baseenum.BaseEnum.CheckParameterExceptionEnum;
import com.sohu.common.exception.CheckException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-14 23:15
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params){
        this.putAll(params);

        if(params.get("page")==null){
        	throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),"parameter-> page 不能为NULL");
        }
        
        if(!StringUtils.isNumeric(params.get("page").toString())){
        	throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),"parameter-> page 应为数字");
        }
        
        if(params.get("limit")==null){
        	throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),"parameter-> limit 不能为NULL");
        }
        
        if(!StringUtils.isNumeric(params.get("limit").toString())){
        	throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),"parameter-> limit 应为数字");
        }
        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("end", limit+(page-1)*limit);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        if(StringUtils.isNotBlank(sidx)){
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if(StringUtils.isNotBlank(order)){
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
