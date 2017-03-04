package com.mattress.service;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.Page;
import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserCustomerView;

public interface ICustomerService extends IBaseService<UserCustomerView, Serializable>{
	List<UserCustomerInfo> queryCustomers(int tiStatus);

	Page<UserCustomerView> queryForPage(int currentPage, int pageSize, int tiStatus);
	
}
