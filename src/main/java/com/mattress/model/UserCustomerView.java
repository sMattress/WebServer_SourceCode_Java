package com.mattress.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class UserCustomerView {

	
	private UserCustomerInfo customer;
	
	
	private List<DeviceInfo> deviceList;
	
	public UserCustomerView() {
	}

	public UserCustomerView(UserCustomerInfo customer, List<DeviceInfo> deviceList) {	
		this.customer = customer;
		this.setDeviceList(deviceList);		
		
	}

	
	public UserCustomerInfo getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(UserCustomerInfo customer){
		this.customer = customer;
	}

	public List<DeviceInfo> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceInfo> deviceList) {
		this.deviceList = deviceList;
	}
	
	

}
