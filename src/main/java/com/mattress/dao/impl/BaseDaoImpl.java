package com.mattress.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mattress.dao.IBaseDao;

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK> {

	private Class<T> entityClass;
	@Autowired
	protected SessionFactory sessionFactory;

	public BaseDaoImpl() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	public void save(T o) {
		if (o != null) {
			getSession().save(o);
		}
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			getSession().delete(o);
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			getSession().update(o);
		}
	}
	
	public List<T> queryList(String hql) {
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()){
				System.out.println(String.format("%s => %s", key, params.get(key)));
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query query = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> queryForPage(String hql, int offset, int length) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return query.list();
	}
	
	@Override
	public List<T> queryForPage(String hql, Map<String, Object> params, int offset, int length) {
		Query query = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return query.list();
	}

	@Override
	public List<T> queryForPage(TypedQuery<T> query) {
		return query.getResultList();
	}
	
	@Override
	public List<T> queryForPage(TypedQuery<T> query, int offset, int length) {
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return query.getResultList();
	}
}
