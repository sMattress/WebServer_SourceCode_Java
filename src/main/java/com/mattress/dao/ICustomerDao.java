package com.mattress.dao;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserCustomerView;
import com.mattress.model.UserManagerInfo;

public interface ICustomerDao extends IBaseDao<UserCustomerView, Serializable>{
	List<UserCustomerInfo> queryCustomers(int tiStatus);

	List<UserCustomerInfo> queryCustomerPage(int offset, int length, int tiStatus);
	
	List<Object[]> queryDevicePageByCustomer(int customerId);

}
