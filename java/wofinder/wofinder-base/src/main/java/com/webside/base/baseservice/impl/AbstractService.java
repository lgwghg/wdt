package com.webside.base.baseservice.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.webside.base.basemapper.BaseMapper;
import com.webside.base.baseservice.BaseService;
import com.webside.exception.ServiceException;

/**
 * 
 * @ClassName: AbstractService
 * @Description: service层基类
 * @author gaogang
 * @date 2016年7月12日 下午3:03:43
 *
 * @param <T>
 *            操作对象类型
 * @param <ID>
 *            id
 */
public class AbstractService<T, ID extends Serializable> implements BaseService<T, ID> {
	public static final Logger logger = Logger.getLogger(AbstractService.class);
	private BaseMapper<T, ID> baseMapper;

	public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public int insert(T t) {
		try {
			return baseMapper.insert(t);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int insertBatch(List<T> t) {
		try {
			return baseMapper.insertBatch(t);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int deleteById(ID id) {
		try {
			return baseMapper.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateById(T t) {
		try {
			return baseMapper.updateById(t);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int deleteBatchById(List<ID> ids) {
		try {
			return baseMapper.deleteBatchById(ids);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public T findById(ID id) {
		try {
			return baseMapper.findById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public T findByName(String name) {
		try {
			return baseMapper.findByName(name);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<T> queryListByPage(Map<String, Object> parameter) {
		try {
			return baseMapper.queryListByPage(parameter);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<T> queryListBatchById(List<ID> ids) {
		try {
			return baseMapper.queryListBatchById(ids);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int count(Map<String, Object> parameter) {
		try {
			return baseMapper.count(parameter);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
