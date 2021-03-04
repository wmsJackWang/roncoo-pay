package com.sohu.mapper.base;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: UserService 
 * @Description: 基础Mapper类
 * @author honghuiwang 
 * @date 2017年8月28日 下午10:26:47 
 *
 */
public interface BaseMappper<T,PK> {
	
	/**
	 * @Title: insert 
	 * @Description: 根据泛型类型插入数据 ,实体属性为null不做插入动作
	 * @param T 泛型
	 * @return void    返回类型 
	 */
	void insert(T t);
	
	/**
	 * @Title: insert 
	 * @Description: 根据泛型类型批量插入数据 ,实体属性为null不做插入动作
	 * @param (List<T> 泛型
	 * @return void    返回类型 
	 */
	void insertBacth(List<T> list);
	
	/**
	 * @Title: update 
	 * @Description: 根据至指定泛型更新数据,实体属性为null不做更新动作
	 * @param T
	 * @return int  返回类型
	 */
	int update(T t);
	
	/**
	 * @Title: update 
	 * @Description: 根据至指定泛型批量更新数据,实体属性为null不做更新动作
	 * @param List<T>
	 * @return int  返回类型
	 */
	int updateBatch(List<T> list);
	
	/**
	 * @Title: delete 
	 * @Description: 根据指定key删除数据
	 * @param id
	 * @return    
	 * int    返回类型
	 */
	@Deprecated
	int delete(PK id);
	

	
	/**
	 * @Title: deleteBatch 
	 * @Description: 根据指定keys删除数据 
	 * @param id Object[]
	 * @return  int 返回类型  
	 */
	@Deprecated
	int deleteBatch(PK[] id);
	
	/**
	 * @Title: queryObject 
	 * @Description: 根据 
	 * @param id 根据指定key查询数据
	 * @return T    返回类型 
	 */
	@Deprecated
	T queryObject(PK id);
	
	/**
	 * @Title: queryList 
	 * @Description: 根据Map<cloume,value>查询结果集 
	 * @param @param map
	 * @param @return    
	 * @return List<T>    返回类型 
	 */
	List<T> queryList(Map<String, Object> map);
	
	/**
	 * @Title: queryList 
	 * @Description: 根据Map<cloume,value>查询结果集 
	 * @param @param map
	 * @param @return    
	 * @return List<T>    返回类型 
	 */
	T queryBean(Map<String, Object> map);
	
	/**
	 * @Title: queryList 
	 * @Description: 根据Map<cloume,value>查询结果集 
	 * @param @param map
	 * @param @return    
	 * @return List<T>    返回类型 
	 */
	List<T> queryListPage(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: queryTotal 
	 * @Description:  根据Map<cloume,value>查询结总数
	 * @param map
	 * @return int    返回类型 
	 *
	 */
	int queryTotal(Map<String, Object> map);
	
	
	
//	int queryTotal();
}
