package com.mattress.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

/**
 * Dao接口 - Dao基接口
 */

public interface IBaseDao<T, PK extends Serializable> {

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public void save(T o);

	
	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void update(T o);
	
	/**
	 * 
	 * @param hql
	 * @return 实体列表
	 */
	public List<T> queryList(String hql);

	/**
	 * 获取对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return
	 */
	public List<T> find(String hql, Map<String, Object> params);

	/**
	 * 分页获取对象列表
	 * 
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> find(String hql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 
	 * @param hql
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<T> queryForPage(String hql, int offset, int length);
	
	/**
	 * 分页获取带参数对象
	 * @param hql
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<T> queryForPage(String hql, Map<String, Object> params, int offset, int length);
	
	/**
	 * 
	 * @param query
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<T> queryForPage(TypedQuery<T> query, int offset, int length);
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<T> queryForPage(TypedQuery<T> query);
}