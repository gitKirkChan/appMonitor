package com.kchan.system.console.api;

import com.kchan.system.console.service.application.CloudOpsServers;
import com.kchan.system.console.service.application.properties.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ApiTestingController {

//    @Autowired
//    private ApplicationProperties info;

    private final CloudOpsServers cloudOpsInfo;

    @Autowired
    public ApiTestingController(CloudOpsServers cloudOpsInfo) {
        this.cloudOpsInfo = cloudOpsInfo;
    }

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/server/dev")
    @ResponseBody
    public List<Server> dev() {
        return cloudOpsInfo.getDev();
    }

    @RequestMapping("/server/test")
    @ResponseBody
    public List<Server> test() {
        return cloudOpsInfo.getTest();
    }

    @RequestMapping("/server")
    @ResponseBody
    public List<Server> servers() { return cloudOpsInfo.getAllServers(); }
}