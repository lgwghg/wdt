package com.webside.base.basemapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @ClassName: BaseMapper
 * @Description: 基础mapper定义,可以自己重写，也可加入自己的方法
 * @author gaogang
 * @date 2016年7月12日 下午3:01:23
 *
 * @param <T>
 *            操作的对象类型
 * @param <ID>
 *            id
 */
public interface BaseMapper<T, ID extends Serializable> {

	int insert(T t);

	int insertBatch(List<T> t);

	int deleteBatchById(List<ID> ids);

	int deleteById(@Param("id") ID id);

	int deleteByEntity(T t);

	int updateById(T t);

	int updateByEntity(T t);

	T findById(@Param("id") ID id);

	T findByName(@Param("name") String name);

	List<T> queryListByPage(Map<String, Object> parameter);

	List<T> queryListByEntity(T t);

	List<T> queryListBatchById(List<ID> ids);

	int count(Map<String, Object> parameter);

	long countByEntity(T t);

}
