package com.mattress.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mattress.dao.IDeviceDao;
import com.mattress.model.DeviceInfo;
import com.mattress.model.Page;
import com.mattress.service.IDeviceService;

@Service
public class DeviceServiceImpl extends BaseServiceImpl<DeviceInfo, Serializable> implements IDeviceService {
	@Autowired
	@Qualifier("deviceDaoImpl")
	private IDeviceDao deviceDao;

	@Override
	public List<DeviceInfo> queryDevices() {
		return deviceDao.queryDevices();
	}
	
	@Override
	public Page<DeviceInfo> queryForPage(int currentPage, int pageSize){
		Page<DeviceInfo> page = new Page<>();
		int deviceAllRow = deviceDao.queryDevices().size();
		int offset = page.countOffset(currentPage, pageSize);
		List<DeviceInfo> deviceList = deviceDao.queryDevicePage(offset, pageSize);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(deviceAllRow);
		page.setList(deviceList);
		return page;
	}
	
	@Override
	public Page<DeviceInfo> queryByStatus(int currentPage, int pageSize, int status){
		Page<DeviceInfo> page = new Page<>();	
		int offset = page.countOffset(currentPage, pageSize);
		List<DeviceInfo> deviceList = deviceDao.queryByStatus(offset, pageSize,status);
		int deviceAllRow = deviceDao.queryByStatus(status).size();
		System.out.println(deviceAllRow);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(deviceAllRow);
		page.setList(deviceList);
		return page;
	}
	@Override
	public void saveDevice(DeviceInfo deviceinfo) {
		deviceDao.saveDevice(deviceinfo);
		
	}
	@Override
	public void chageDeviceStatus(DeviceInfo deviceinfo) {
		deviceDao.changeDeviceStatus(deviceinfo);
		
	}
}
