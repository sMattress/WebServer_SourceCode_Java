package com.mattress.controller.sys;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mattress.controller.user.AppResponse;
import com.mattress.utils.ErrCodeMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/sys")
public class SysController {

    @GetMapping("/validate/time")
    public AppResponse validateTime() {
        return AppResponse.success().addParam(new HashMap<String, String>() {{
            put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        }});
    }

    @GetMapping("/apps/update")
    public AppResponse appsUpdate(@RequestParam final String version_code) {
        try {

            System.out.println();
            final BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("IntelligentMattress.root") + "/WEB-INF/apps-info.json"));

            String content = "";
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                content += tempString;
            }
            JSONObject config = JSON.parseObject(content);

            final String vNameLatest = (String) config.get("version_name");
            final int vCodeLatest = (int) config.get("version_code");


            if (vCodeLatest > Integer.valueOf(version_code)) {
                return AppResponse.success().addParam(new HashMap<String, Object >() {{
                    put("latest", new HashMap<String, String>() {{
                        put("version_name", vNameLatest);
                        put("version_code", String.valueOf(vCodeLatest));
                    }});
                    put("download", config.get("download_android"));
                }});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return AppResponse.failure(ErrCodeMap.UNREALIZED_FUNCTION);
    }

}
