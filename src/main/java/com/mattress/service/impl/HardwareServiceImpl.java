package com.mattress.service.impl;

import com.mattress.controller.user.AppResponse;
import com.mattress.dao.IDeviceDao;
import com.mattress.model.DeviceInfo;
import com.mattress.service.IHardwareService;
import com.mattress.utils.ErrCodeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class HardwareServiceImpl implements IHardwareService {

    @Autowired
    @Qualifier("deviceDaoImpl")
    private IDeviceDao deviceDao;

    @Override
    public DeviceInfo getDevice(String name) {
        final List<DeviceInfo> devices = deviceDao.find("FROM DeviceInfo device WHERE device.vcName = :name", new HashMap<String, Object>() {{
            put("name", name);
        }});

        return devices == null || devices.isEmpty() ? null : devices.get(0);
    }

    @Override
    public AppResponse updateDeviceState(DeviceInfo device, int status) {
        if (device == null) return AppResponse.failure(ErrCodeMap.UNREALIZED_FUNCTION);
        device.setIStatus(status);
        deviceDao.update(device);
        return AppResponse.success();
    }
}
