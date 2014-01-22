package com.htlab.mis.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htlab.mis.dao.BaseDao;
import com.htlab.mis.service.BaseService;

/**
 * 此类型主要用于有关数据库操作的Service类的父类，用于操作一些公共操作的方法
 *
 */
public  class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T,PK> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	public BaseDao<T, PK> baseDao;

	
	public List<T> getAll() {
		return this.baseDao.getAll();
	}

	
	public T get(PK id) {
		return this.baseDao.get(id);
	}

	
	public T load(PK id) {
		return this.baseDao.load(id);
	}

	
	public Serializable save(T entity) {
		return this.baseDao.save(entity);
	}

	
	public void saveOrUpdate(T entity) {
		this.baseDao.saveOrUpdate(entity);
		
	}

	
	public void saveOrUpdateAll(Collection<T> entities) {
		this.baseDao.saveOrUpdateAll(entities);
		
	}

	
	public void update(T entity) {
		this.baseDao.update(entity);
		
	}

	
	public T merge(T entity) {
		return this.baseDao.merge(entity);
	}

	
	public void delete(T entity) {
		this.baseDao.delete(entity);
		
	}

	
	public void removeById(PK id) {
		this.baseDao.removeById(id);
		
	}

	
	public List<T> findByProperty(String name, Object value) {
		return this.baseDao.findByProperty(name, value);
	}

	
	public T findUniqueResultByProperty(String name, Object value) {
		return this.baseDao.findUniqueResultByProperty(name, value);
	}

	
	public Long countEntity(Class<T> cls) {
		return this.baseDao.countEntity(cls);
	}
	
}
