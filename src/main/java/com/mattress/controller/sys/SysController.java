package com.mattress.controller.sys;


import com.alibaba.fastjson.JSONObject;
import com.mattress.controller.user.AppResponse;
import com.mattress.model.DeviceInfo;
import com.mattress.model.UserBindDevice;
import com.mattress.model.UserCustomerInfo;
import com.mattress.service.IUserService;
import com.mattress.utils.AppDeployConfig;
import com.mattress.utils.ErrCodeMap;
import com.mattress.utils.UrlMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/sys")
public class SysController {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    @GetMapping("/auth")
    public AppResponse auth(@RequestParam String from, @RequestParam String to) {

        // 只允许：
        //   用户 => 硬件
        //   硬件 => 用户
        final UserCustomerInfo fromUser = userService.getUser(from);
        final DeviceInfo toDevice = userService.getDevice(to);
        if (fromUser != null && toDevice != null) {
            final UserBindDevice bindShip = userService.getBindShip(fromUser, toDevice);
            if (bindShip != null) {
                return AppResponse.success();
            }
        }

        final DeviceInfo fromDevice = userService.getDevice(from);
        final UserCustomerInfo toUser = userService.getUser(to);
        if (fromDevice != null && toUser != null) {
            final UserBindDevice bindShip = userService.getBindShip(toUser, fromDevice);
            if (bindShip != null) {
                return AppResponse.success();
            }
        }

        return AppResponse.failure(ErrCodeMap.PERMISSION_DENIED);
    }

    @GetMapping("/validate/time")
    public AppResponse validateTime() {
        return AppResponse.success().addParam(new HashMap<String, String>() {{
            put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        }});
    }

    @GetMapping("/apps/update")
    public AppResponse appsUpdate(@RequestParam final String version_code) {


        JSONObject config = AppDeployConfig.getInstance();

        final String vNameLatest = (String) config.get("version_name");
        final int vCodeLatest = (int) config.get("version_code");


        if (vCodeLatest > Integer.valueOf(version_code)) {
            return AppResponse.success().addParam(new HashMap<String, Object>() {{
                put("latest", new HashMap<String, String>() {{
                    put("version_name", vNameLatest);
                    put("version_code", String.valueOf(vCodeLatest));
                }});
                put("download", config.get("download_android"));
            }});
        }

        return AppResponse.failure(ErrCodeMap.UNREALIZED_FUNCTION);
    }

    @GetMapping("/validate/token")
    public AppResponse validateToken(@RequestParam String account, @RequestParam String timestamp, @RequestParam String sign) {

        final UserCustomerInfo user = userService.getUser(account);
        if (user == null) {
            return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
        }

        final AppResponse res = userService.validatesSign(account, UrlMap.VALIDATE_TOKEN, timestamp, sign);

        if (res.getFlag() == 1) {
            userService.setUserState(user, 0);
        }

        return res;

    }

}
