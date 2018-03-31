package com.fedex.plefs.system.console.api;

import com.fedex.plefs.system.console.domain.ApplicationProperties;
import com.fedex.plefs.system.console.domain.CloudOpsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@EnableAutoConfiguration
public class ApiTestingController {

    @Autowired
    private ApplicationProperties info;

    @Autowired
    private CloudOpsProperties cloudOpsInfo;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/app")
    @ResponseBody
    String appTest() {
        return info.getName();
    }

    @RequestMapping("/versions")
    @ResponseBody
    ApplicationProperties.Version versions() { return info.getVersion(); }

    @RequestMapping("/dev")
    @ResponseBody
    List<CloudOpsProperties.Dev> dev() {
        return cloudOpsInfo.getDev();
    }

    @RequestMapping("/test")
    @ResponseBody
    List<CloudOpsProperties.Test> test() {
        return cloudOpsInfo.getTest();
    }
}