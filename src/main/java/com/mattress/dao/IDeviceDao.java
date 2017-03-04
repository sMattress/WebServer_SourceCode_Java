package com.mattress.dao;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.DeviceInfo;
import com.mattress.model.UserCustomerInfo;

public interface IDeviceDao extends IBaseDao<DeviceInfo, Serializable>{
	List<DeviceInfo> queryDevices();
	List<DeviceInfo> queryDevicePage(int offset, int length);
	List<DeviceInfo> queryByStatus(int status);
	List<DeviceInfo> queryByStatus(int offset, int length ,int status);
	void saveDevice(DeviceInfo deviceinfo);	
	void changeDeviceStatus(DeviceInfo deviceinfo);
}
