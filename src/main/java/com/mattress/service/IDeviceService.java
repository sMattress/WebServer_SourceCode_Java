package com.mattress.service;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.DeviceInfo;
import com.mattress.model.Page;

public interface IDeviceService extends IBaseService<DeviceInfo, Serializable> {
	
	List<DeviceInfo> queryDevices();
	
	Page<DeviceInfo> queryForPage(int currentPage, int pageSize);
	
	Page<DeviceInfo> queryByStatus(int currentPage, int pageSize, int status);
	
	void saveDevice(DeviceInfo deviceinfo);
	
	void chageDeviceStatus(DeviceInfo deviceinfo);
}
