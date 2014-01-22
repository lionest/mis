package com.htlab.mis.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
* @ClassName: BaseService 
* @Description: 定义几个常用的service公共方法
* @author zouxiaoming
* @date Apr 18, 2012 5:09:28 PM 
* 
* @param <T>
* @param <PK>
 */
public interface BaseService<T, PK extends Serializable> {
	/**
	 * 获得所有的实体对象
	 * @return
	 */
	public List<T> getAll();
	
	/**
	 * 根据ID查询指定的对象
	 * @param id
	 * @return
	 */
	public T get(PK id);
	
	/**
	 * 根据ID加载指定的对象
	 * @param id
	 * @return
	 */
	public T load(PK id);
	
	/**
	 * 保存指定的对象
	 * @param object
	 * @return
	 */
	public Serializable save(T entity);
	
	
	/**
	 * 保存或者更新指定的对象 根据对象hibernate的状态
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	
	
	/**
	 * 将一个容器中的所有对象保存或者更新到数据库
	 * @param entities
	 */
	public void saveOrUpdateAll(Collection<T> entities);
	
	/**
	 * 更新指定的对象
	 * @param object
	 * @return
	 */
	public void update(T entity);
	
	/**
	 * 合并指定的对象
	 * @param object
	 * @return
	 */
	public T merge(T entity);
	
	/**
	 * 根据删除指定的对象
	 * @param id
	 */
	public void delete(T entity);
	
	/**
	 * 根据ID删除指定的对象
	 * @param id
	 */
	public void removeById(PK id);
	
	/**
	 * 根据指定的属性名及属性值返回一个对象列表
	 * @param entityClass
	 * @param name
	 * @param value
	 * @return
	 */
	public List<T> findByProperty(String name, Object value);
	
	/**
	 * 根据指定的属性名及属性值取返回一个对象
	 * @param entityClass
	 * @param name
	 * @param value
	 * @return
	 */
	public T findUniqueResultByProperty(String name, Object value);
	
	/**
	 * 获取某个持久化类的记录数
	 * @author zouxiaoming
	 * @param cls
	 * @return
	 */
	public Long countEntity(Class<T> cls);

}
