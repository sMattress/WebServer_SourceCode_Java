package com.mattress.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mattress.dao.IBaseDao;
import com.mattress.service.IBaseService;
@Service
public class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK>{  
	
	@Autowired
	@Qualifier("baseDaoImpl")
    private IBaseDao<T, PK> baseDao;    
  
    public T get(PK id) {  
        return baseDao.get(id);  
    }  
      
    public void save(T o) {  
         baseDao.save(o);  
    }
    
    @Override
	public void update(T o) {
		// TODO Auto-generated method stub
		baseDao.update(o);
	}

	@Override
	public void delete(T o) {
		// TODO Auto-generated method stub
		baseDao.delete(o);
	}  
  
    

	@Override
	public List<T> queryList(String hql) {
		return baseDao.queryList(hql);
	}

	
}  