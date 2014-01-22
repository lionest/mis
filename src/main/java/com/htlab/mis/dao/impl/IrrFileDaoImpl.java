package com.htlab.mis.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.htlab.mis.dao.IrrFileDao;
import com.htlab.mis.entity.IrrFile;

@Repository
public class IrrFileDaoImpl extends BaseDaoImpl<IrrFile, Integer> implements IrrFileDao {
	
	@Override
	public void removeAll() {
		try {
			getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				public Integer doInHibernate(Session session) {
					String sql="delete from irr_file";
					Query query = session.createSQLQuery(sql);
					int i=query.executeUpdate();
					return i;
				}
			});
		} catch (DataAccessException e) {
			log.error("Error executeUpdate:{}", e);
		}
	}
}
