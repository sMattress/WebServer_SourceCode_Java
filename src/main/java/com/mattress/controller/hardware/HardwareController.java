package com.mattress.controller.hardware;

import com.mattress.controller.user.AppResponse;
import com.mattress.dao.IDeviceDao;
import com.mattress.model.DeviceInfo;
import com.mattress.service.IHardwareService;
import com.mattress.service.IUserService;
import com.mattress.utils.ErrCodeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/hardware")
public class HardwareController {

    @Autowired
    @Qualifier("hardwareServiceImpl")
    private IHardwareService hardwareService;

    @GetMapping("/connect")
    public AppResponse connect(@RequestParam final String name) {
        return hardwareService.updateDeviceState(hardwareService.getDevice(name), 4);
    }

    @GetMapping("/disconnect")
    public AppResponse disConnect(@RequestParam final String name) {
        return hardwareService.updateDeviceState(hardwareService.getDevice(name), 3);

    }

}
