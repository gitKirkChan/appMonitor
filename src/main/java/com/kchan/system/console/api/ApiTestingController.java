package com.kchan.system.console.api;

import com.kchan.system.console.service.application.CloudOpsService;
import com.kchan.system.console.service.application.properties.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ApiTestingController {

//    @Autowired
//    private ApplicationProperties info;

    private final CloudOpsService cloudOpsInfo;

    @Autowired
    public ApiTestingController(CloudOpsService cloudOpsInfo) {
        this.cloudOpsInfo = cloudOpsInfo;
    }

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/server/dev")
    @ResponseBody
    public List<Application> dev() {
        return cloudOpsInfo.getDev();
    }

    @RequestMapping("/server/test")
    @ResponseBody
    public List<Application> test() {
        return cloudOpsInfo.getTest();
    }

    @RequestMapping("/server")
    @ResponseBody
    public List<Application> servers() { return cloudOpsInfo.getAllServers(); }
}