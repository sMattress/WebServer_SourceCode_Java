package com.mattress.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mattress.dao.ICustomerDao;
import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserCustomerView;


@Repository
public class CustomerDaoImpl extends BaseDaoImpl<UserCustomerView, Serializable> implements ICustomerDao {

	private List<UserCustomerView> customerList = new ArrayList<UserCustomerView>();
	
	@Override
	public List<UserCustomerInfo> queryCustomers(int tiStatus) {
		
		String hql = "FROM UserCustomerInfo customer";
		if (tiStatus == 1){
			 hql = "FROM UserCustomerInfo customer WHERE customer.tiStatus="+tiStatus;
		}		
		
		List list= queryList(hql);
		return list;
	}
	
	
	@Override
	public List<UserCustomerInfo> queryCustomerPage(int offset, int length, int tiStatus) {	
		
		String hql = "FROM UserCustomerInfo customer";
		if (tiStatus == 1){
			 hql = "FROM UserCustomerInfo customer WHERE customer.tiStatus="+tiStatus;
		}		
		List list = queryForPage(hql, offset, length);
//		System.out.println("list" + list.size());
		return list;
	}	
	



	@Override
	public List<Object[]> queryDevicePageByCustomer(int customerId) {

		String hql = "SELECT bind.TTime,device.IId,device.vcName,device.IStatus FROM UserBindDevice AS bind,DeviceInfo AS device "
				+ "WHERE bind.fkDeviceId = device.IId AND bind.fkCustomerId=" + customerId;
		List list = queryList(hql);
		
		return list;
	}	

}
