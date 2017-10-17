package com.webside.base.baseservice;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: BaseService
 * @Description: service层接口
 * @author gaogang
 * @date 2016年7月12日 下午3:03:19
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID extends Serializable> {

	/**
	 * 
	 * 插入对象
	 * 
	 * @return 返回影响的行数
	 */
	int insert(T t);

	/**
	 * 
	 * 批量插入对象
	 * 
	 * @return 返回影响的行数
	 */
	int insertBatch(List<T> t);

	/**
	 * 
	 * 批量删除
	 * 
	 * @return 返回影响的行数
	 */
	int deleteBatchById(List<ID> ids);

	/**
	 * 
	 * 根据id删除
	 * 
	 * @return 返回影响的行数
	 */
	int deleteById(ID id);

	/**
	 * 
	 * 根据ID更新对象
	 * 
	 * @return 返回影响的行数
	 */
	int updateById(T t);

	/**
	 * 
	 * 根据id查询对象
	 * 
	 * @return 返回查询的对象
	 */
	T findById(ID id);

	/**
	 * 
	 * 根据名称查询
	 * 
	 * @return 返回查询的对象
	 */
	T findByName(String name);

	/**
	 * 
	 * 根据分页参数查询对象
	 * 
	 * @return 返回查询的对象集合
	 */
	List<T> queryListByPage(Map<String, Object> parameter);

	/**
	 * 
	 * 根据ID批量查询对象
	 * 
	 * @return 返回查询的对象集合
	 */
	List<T> queryListBatchById(List<ID> ids);

	/**
	 * 
	 * 根据参数查询对象总条数
	 * 
	 * @return 返回查询的对象总条数
	 */
	int count(Map<String, Object> parameter);

}
