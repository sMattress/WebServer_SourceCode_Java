package com.mattress.controller.download;

import com.alibaba.fastjson.JSONObject;
import com.mattress.utils.AppDeployConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/app")
public class DownloadController {

    @RequestMapping(value = "/download", method = { RequestMethod.GET })
    public String getUrl(@RequestParam String platform) {

        final JSONObject config = AppDeployConfig.getInstance();

        switch (platform.toLowerCase()) {
            case "ios":
                return "redirect:" + config.getString("download_ios");
            case "android":
                return "redirect:" + config.getString("download_android");
            default:
                return "redirect:/";
        }

    }


}
