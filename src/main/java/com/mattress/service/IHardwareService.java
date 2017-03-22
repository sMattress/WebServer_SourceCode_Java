package com.mattress.service;


import com.mattress.controller.user.AppResponse;
import com.mattress.model.DeviceInfo;

public interface IHardwareService {

    DeviceInfo getDevice(String name);
    AppResponse updateDeviceState(DeviceInfo device, int status);

}
