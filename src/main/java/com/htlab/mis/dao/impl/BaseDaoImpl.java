package com.htlab.mis.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.htlab.mis.common.Validity;
import com.htlab.mis.dao.BaseDao;
import com.htlab.mis.util.GenericsUtil;
import com.htlab.mis.util.PageInfo;

/**
 * 扩展自Appfuse框架的DAO基础实现类，支付分页查询操作 因为利用的java的泛型机制，所以使用时要指定一个明确的类 该类实现了BaseDao<T,
 * PK>接口、对通过的CURD及分页查询操作提供了默认实现， 派生类类不需要再一一来实现，从而提高了开发效率 使用示例: UserDaoHibernate
 * extends BaseDaoImpl<User, Integer> { ... }
 */

public class BaseDaoImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements BaseDao<T, PK> {
	private Class<T> persistentClass; // 用于保存构造时指定的具体持久化类
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * 构造方法
	 * 
	 * @param persistentClass
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// 构造时对泛型的类型进行初始化
		this.persistentClass = GenericsUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 获得所有的实体对象
	 * 
	 * @return
	 */
	public List<T> getAll() {
		return super.getHibernateTemplate().loadAll(this.persistentClass);
	}

	/**
	 * 根据ID查询指定的对象
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id) {
		return (T) this.getHibernateTemplate().get(this.persistentClass, id);
	}

	/**
	 * 根据ID加载指定的对象
	 * 
	 * @param id
	 * @return
	 */
	public T load(PK id) {
		return (T) this.getHibernateTemplate().load(this.persistentClass, id);
	}

	/**
	 * 保存指定的对象
	 * 
	 * @param object
	 * @return
	 */
	public Serializable save(T entity) {
		return super.getHibernateTemplate().save(entity);
	}

	/**
	 * 保存或者更新指定的对象 根据对象hibernate的状态
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 将一个容器中的所有对象保存或者更新到数据库
	 * 
	 * @param entities
	 */
	public void saveOrUpdateAll(Collection<T> entities) {
		this.getHibernateTemplate().saveOrUpdateAll(entities);

	}

	/**
	 * 更新指定的对象
	 * 
	 * @param object
	 * @return
	 */
	public void update(T entity) {
		super.getHibernateTemplate().update(entity);
	}

	/**
	 * 合并指定的对象
	 * 
	 * @param object
	 * @return
	 */
	public T merge(T entity) {
		return (T) super.getHibernateTemplate().merge(entity);
	}

	/**
	 * 根据删除指定的对象
	 * 
	 * @param id
	 */
	public void delete(T entity) {
		super.getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据ID删除指定的对象
	 * 
	 * @param id
	 */
	public void removeById(PK id) {
		super.getHibernateTemplate().delete(this.get(id));
	}

	/**
	 * 获取某个持久化类的记录数
	 * 
	 * @author zouxiaoming
	 * @param cls
	 * @return
	 */
	public Long countEntity(Class<T> cls) {
		return (Long) this.getUniqueBeanResult(
				"select count(*) from " + cls.getName(), null);
	}

	/**
	 * 根据命名参数进行HQL查询
	 * 
	 * @param queryName
	 * @param queryParams
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryString,
			Map<String, Object> queryParams) {
		final String hql = queryString;
		final Map<String, Object> properties = queryParams;
		this.getHibernateTemplate().setCacheQueries(true);
		return getHibernateTemplate().executeFind(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery(hql);
						if (properties != null && properties.size() > 0) {
							query.setProperties(properties);
						}
						return query.list();
					}
				});
	}

	/**
	 * 根据指定的属性名及属性值返回一个对象列表
	 * 
	 * @param entityClass
	 * @param name
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String name, Object value) {
		String className = this.persistentClass.getName();
		StringBuffer sb = new StringBuffer(100);
		sb.append("from ");
		sb.append(className);
		sb.append(" as e where e.");
		sb.append(name);
		sb.append("=? and e.status=0");
		return this.getHibernateTemplate().find(sb.toString(), value);
	}

	/**
	 * 根据指定的属性名及属性值取返回一个对象
	 * 
	 * @param entityClass
	 * @param name
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueResultByProperty(String name, final Object value) {
		String className = persistentClass.getName();
		StringBuffer sb = new StringBuffer(100);
		sb.append("from ");
		sb.append(className);
		sb.append(" as e where e.");
		sb.append(name);
		sb.append("=?");

		final String hql = sb.toString();

		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createQuery(hql).setMaxResults(1)
								.setParameter(0, value).list();
					}

				});
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 根据HQL语句返回包含实体的List
	 * 
	 * @param hql
	 *            查询的HQL语句
	 * @return 返回包含查询所得实体的List
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHql(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 通过HQL查询数据列表
	 * 
	 * @param hql
	 *            查询的hql语句
	 * @param properties
	 *            需要传入的参数
	 * @return 返回包含查询所得实体的List
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(final String hql, final Map<String, Object> properties) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (properties != null && properties.size() > 0) {
					query.setProperties(properties);
				}
				return query.list();
			}
		});
	}

	/**
	 * 通过HQL查询数据列表
	 * 
	 * @param hql
	 *            查询的hql语句
	 * @param properties
	 *            需要传入的参数
	 * @return 返回包含查询所得实体的List
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(final String hql, final int maxResult,
			final Map<String, Object> properties) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (properties != null && properties.size() > 0) {
					query.setProperties(properties);
				}
				if (maxResult > 0) {
					query.setMaxResults(maxResult);
				}
				return query.list();
			}
		});
	}

	/**
	 * 通过HQL查询数据列表 可以转换成需要的Bean
	 * 
	 * @param hql
	 *            查询的hql语句
	 * @param properties
	 *            需要传入的参数
	 * @param cls
	 *            对查询结果需要转换成的Bean
	 * @return 返回包含查询所得实体的List
	 */
	@SuppressWarnings("rawtypes")
	public List findByHqlResultBean(final String hql, final Class cls,
			final Map<String, Object> properties) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql).setResultTransformer(
						Transformers.aliasToBean(cls));
				if (properties != null && properties.size() > 0) {
					query.setProperties(properties);
				}
				return query.list();
			}
		});
	}

	/**
	 * 通过SQL查询数据列表
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @return 返回包含查询所得实体的List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List findBySql(final String sql) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}

	/**
	 * 通过SQL查询数据列表
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param properties
	 *            需要传入的参数
	 * @return 返回包含查询所得实体的List
	 */
	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, final Map<String, Object> properties) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				if (properties != null && properties.size() > 0) {
					query.setProperties(properties);
				}
				return query.list();
			}
		});
	}

	/**
	 * 执行批量更新
	 * 
	 * @param hql
	 *            要执行批量更新的HQL语句
	 * @param params
	 *            参数列表
	 * @return
	 */
	public int bulkUpdate(String hql, Object[] params) {
		return this.getHibernateTemplate().bulkUpdate(hql, params);
	}

	/**
	 * 执行批量更新,没有参数
	 * 
	 * @param hql
	 * @return
	 */
	public int bulkUpdate(String hql) {
		return this.getHibernateTemplate().bulkUpdate(hql);
	}

	/**
	 * 通过SQL修改
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param properties
	 *            需要传入的参数
	 * @return 返回包含查询所得实体的List
	 */
	public Object updateBySql(final String sql,
			final Map<String, Object> properties) {
		return this.getHibernateTemplate().execute(
				new HibernateCallback<Object>() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						if (properties != null && properties.size() > 0) {
							query.setProperties(properties);
						}
						return query.executeUpdate();
					}
				});
	}

	protected String getCountHql(String hql) {
		String condition = hql
				.substring(hql.toLowerCase().lastIndexOf("from") + 4);
		String tmp = condition.toLowerCase();
		int pos = tmp.lastIndexOf(" order ");
		if (pos > 0) {
			condition = condition.substring(0, pos);
		}

		condition = condition.replaceAll("fetch", ""); // 去掉fetch

		return "select count(*) from " + condition;
	}

	/**
	 * 根据HQL语句返回包含实体的自定义分页数据结构PageInfo
	 * 
	 * @param pageIndex
	 *            查询的当前页号，从1开始计数
	 * @param pageSize
	 *            每页所显示的最多记录数
	 * @param hql
	 *            查询的HQL语句，含查询参数
	 * @param params
	 *            hql语句中对应的参数数组
	 * @return 返回当前分页信息的PageInfo对象
	 */
	@SuppressWarnings("rawtypes")
	public PageInfo findPageInfoByHql(final int pageIndex, final int pageSize,
			final String hql, final Map<String, Object> properties) {
		log.debug("hql= " + hql);
		log.debug("properties= " + properties);
		// 求未分页的总记录数
		Integer rowCount = 0;
		String condition = hql
				.substring(hql.toLowerCase().lastIndexOf("from") + 4);
		String tmp = condition.toLowerCase();
		int pos = tmp.lastIndexOf(" order ");
		if (pos > 0) {
			condition = condition.substring(0, pos);
		}

		condition = condition.replaceAll("fetch", ""); // 去掉fetch
		rowCount = ((Long) getUniqueBeanResult("select count(*) from "
				+ condition, properties)).intValue();

		int pageCount = (int) ((rowCount + pageSize - 1) / pageSize);
		if (pageIndex > pageCount) {// 页面超过总页数，则返回null
			return new PageInfo(new ArrayList(), rowCount, pageIndex, pageSize);
		}
		final int currentPageIndex = pageIndex <= pageCount ? pageIndex
				: pageCount;

		// 求分页后的数据
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						try {
							int firstResult = (((currentPageIndex) >= 1 ? currentPageIndex
									: 1) - 1)
									* pageSize;
							Query query = session.createQuery(hql)
									.setFirstResult(firstResult)
									.setMaxResults(pageSize);

							if (properties != null) {
								query.setProperties(properties);
							}

							List l = query.list();
							return l;
						} catch (Exception ex) {
							ex.printStackTrace();
							return null;
						}
					}
				});

		return new PageInfo(list, rowCount, pageIndex, pageSize);
	}

	/**
	 * 根据SQL语句返回包含实体的自定义分页数据结构PageInfo
	 * 
	 * @param pageIndex
	 *            查询的当前页号，从1开始计数
	 * @param pageSize
	 *            每页所显示的最多记录数
	 * @param hql
	 *            查询的HQL语句，含查询参数
	 * @param params
	 *            hql语句中对应的参数数组
	 * @return 返回当前分页信息的PageInfo对象
	 */
	@SuppressWarnings("rawtypes")
	public PageInfo findPageInfoBySql(final int pageIndex, final int pageSize,
			final String sql, final Map<String, Object> properties) {
		// 求未分页的总记录数
		Integer rowCount = 0;
		String condition = sql
				.substring(sql.toLowerCase().lastIndexOf("from") + 4);
		String tmp = condition.toLowerCase();
		int pos = tmp.lastIndexOf(" order ");
		if (pos > 0) {
			condition = condition.substring(0, pos);
		}

		condition = condition.replaceAll("fetch", ""); // 去掉fetch
		rowCount = ((BigInteger) getUniqueBeanResultBySql(
				"select count(*) from " + condition, properties)).intValue();

		int pageCount = (int) ((rowCount + pageSize - 1) / pageSize);
		if (pageIndex > pageCount) {// 页面超过总页数，则返回null
			return null;
		}
		final int currentPageIndex = pageIndex <= pageCount ? pageIndex
				: pageCount;

		// 求分页后的数据
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						try {
							int firstResult = (((currentPageIndex) >= 1 ? currentPageIndex
									: 1) - 1)
									* pageSize;
							Query query = session.createSQLQuery(sql)
									.addEntity(persistentClass)
									.setFirstResult(firstResult)
									.setMaxResults(pageSize);

							if (properties != null) {
								query.setProperties(properties);
							}

							List l = query.list();
							return l;
						} catch (Exception ex) {
							ex.printStackTrace();
							return null;
						}
					}
				});

		return new PageInfo(list, rowCount, pageIndex, pageSize);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getUniqueBeanResult(final String hql,
			final Map<String, Object> properties) throws DataAccessException {

		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				if (properties != null) {
					query.setProperties(properties);
				}
				Object object = query.uniqueResult();
				return object;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getUniqueBeanResultBySql(final String sql,
			final Map<String, Object> properties) throws DataAccessException {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createSQLQuery(sql);
				if (properties != null) {
					query.setProperties(properties);
				}
				Object object = query.uniqueResult();
				return object;
			}
		});
	}

	/**
	 * execute with sql and param arrary
	 * 
	 * @param sql
	 *            according sql
	 * @param values
	 *            according param arrary
	 * @return the count of success record
	 * @throws HibernateDaoSupportException
	 *             when accessing and manipulating database happen exception
	 */
	public int bulkUpdateSql(final String sql, final Map<String, Object> values) {
		try {
			return getHibernateTemplate().execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) {
							Query query = session.createSQLQuery(sql);
							if (values != null && values.size() > 0) {
								query.setProperties(values);
							}
							int i = query.executeUpdate();
							return i;
						}
					});
		} catch (DataAccessException e) {
			log.error("Error executeUpdate:{}", e);
			return -1;
		}
	}

	/**
	 * update entity with hql and param map
	 * 
	 * @param hql
	 *            according hql
	 * @param properties
	 *            according param map
	 * @return the count of success record
	 */
	public int bulkUpdate(final String hql, final Map<String, Object> properties) {
		try {
			return getHibernateTemplate().execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) {
							Query query = session.createQuery(hql);
							if (!Validity.isEmpty(properties)) {
								query.setProperties(properties);
							}
							int i = query.executeUpdate();
							return i;
						}
					});
		} catch (DataAccessException e) {
			log.error("Error executeUpdate:{}", e);
			return -1;
		}
	}

}
