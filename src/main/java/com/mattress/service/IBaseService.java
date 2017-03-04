package com.mattress.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T, PK extends Serializable> {  
  
    /** 
     * 根据ID获取实体对象. 
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
     * 更新实体对象
     * @param o
     */
    public void update(T o);
    
    /**
     * 删除实体对象
     * @param o
     */
    public void delete(T o);
    
    /**
     * 
     * @param hql
     * @return 实体列表
     */
    public List<T> queryList(String hql);
}  
