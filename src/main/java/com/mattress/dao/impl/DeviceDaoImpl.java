package com.mattress.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mattress.dao.IDeviceDao;
import com.mattress.model.DeviceInfo;

@Repository
public class DeviceDaoImpl extends BaseDaoImpl<DeviceInfo, Serializable> implements IDeviceDao {
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	public List<DeviceInfo> queryDevices() {
		String hql = "FROM DeviceInfo device";
		return queryList(hql);
	}
	@Override
	public List<DeviceInfo> queryDevicePage(int offset, int length){
		String hql = "FROM DeviceInfo device";
		return queryForPage(hql,offset,length);	
	}
	
	

	
	@Override
	public List<DeviceInfo> queryByStatus(int offset, int length ,int status) {	
		Session session = sessionFactory.openSession();

		TypedQuery<DeviceInfo> query = session.createQuery("SELECT t FROM DeviceInfo t WHERE t.IStatus = ?",DeviceInfo.class);
		query.setParameter(0,status);
		if (status == 1) {
			query = session.createQuery("SELECT t FROM DeviceInfo t WHERE t.IStatus = ? OR t.IStatus = ?",DeviceInfo.class);
			query.setParameter(0,3);
			query.setParameter(1,4);
		}
	  /*  List<DeviceInfo> result = query.getResultList();*/
	    List<DeviceInfo> result = queryForPage(query, offset, length);
	    session.close();
	    return result;
	}
	
	@Override
	public void saveDevice(DeviceInfo deviceinfo) {
		Session session = sessionFactory.openSession();
		session.getSession().save(deviceinfo);
		session.close();
		
	}
	@Override
	public List<DeviceInfo> queryByStatus(int status) {
//		Session session = sessionFactory.openSession();
//		TypedQuery<DeviceInfo> query = session.createQuery("SELECT t FROM DeviceInfo t WHERE t.IStatus = ?",DeviceInfo.class);
//		query.setParameter(0,status);
//	    List<DeviceInfo> result = queryForPage(query);
//	    session.close();
	    return queryByStatus(0, Integer.MAX_VALUE, status);
	}
	@Override
	public void changeDeviceStatus(DeviceInfo deviceinfo) {
		Session session = sessionFactory.openSession(); 
        session.beginTransaction();     
		int status = deviceinfo.getIStatus();
		int id = deviceinfo.getIId();
    	String hql = "update DeviceInfo t set t.IStatus = '"+status+"' where IId ="+id;
        Query query = session.createQuery(hql);  
        query.executeUpdate();  
        session.getTransaction().commit(); 
	    session.close();
		
	}
	
	
}
