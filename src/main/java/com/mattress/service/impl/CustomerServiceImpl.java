package com.mattress.service.impl;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mattress.common.ComputeTool;
import com.mattress.dao.ICustomerDao;
import com.mattress.model.DeviceInfo;
import com.mattress.model.KnowledgeInfo;
import com.mattress.model.Page;
import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserCustomerView;
import com.mattress.service.ICustomerService;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<UserCustomerView, Serializable> implements ICustomerService {
	@Autowired
	@Qualifier("customerDaoImpl")
	private ICustomerDao customerDao;

	@Override
	public List<UserCustomerInfo> queryCustomers(int tiStatus) {
		return customerDao.queryCustomers(tiStatus);
	}


	@Override
	public Page<UserCustomerView> queryForPage(int currentPage, int pageSize, int tiStatus) {	
		Page<UserCustomerView> page = new Page<>();

		int allRow = customerDao.queryCustomers(tiStatus).size();
		int offset = page.countOffset(currentPage, pageSize);

		List<UserCustomerInfo> customerList = customerDao.queryCustomerPage(offset, pageSize, tiStatus);
		
		List<UserCustomerView> customerViewList = new ArrayList<>();	
		for (int i = 0; i < customerList.size(); i++){		
			
			UserCustomerInfo customer = customerList.get(i);
			UserCustomerView customerView = new UserCustomerView();
			List<DeviceInfo> deviceList = new ArrayList<>();
			System.out.println("customer iid is " + customer.getIId());
			List<Object[]> obj = customerDao.queryDevicePageByCustomer(customer.getIId());	
			for (int j = 0; j < obj.size(); j++){
				Object[] object = obj.get(j);

				DeviceInfo device = new DeviceInfo();			
				device.setTTime(ComputeTool.convertStr2Date(object[0].toString()));
				
				device.setIId(Integer.valueOf(object[1].toString()));
				device.setVcName(object[2].toString());
				device.setIStatus(Integer.valueOf(object[3].toString()));
				deviceList.add(device);
			}
			customerView.setDeviceList(deviceList);
			customerView.setCustomer(customer);
			customerViewList.add(customerView);
		}					
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		page.setList(customerViewList);
		
		return page;
	}

	
}
