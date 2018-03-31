package com.fedex.plefs.system.console.api;

import com.fedex.plefs.system.console.domain.CloudOpsServers;
import com.fedex.plefs.system.console.domain.server.Server;
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

//    @RequestMapping("/app")
//    @ResponseBody
//    String appTest() {
//        return info.getName();
//    }
//
//    @RequestMapping("/versions")
//    @ResponseBody
//    ApplicationProperties.Version versions() { return info.getVersion(); }

    @RequestMapping("/dev")
    @ResponseBody
    public List<Server> dev() {
        return cloudOpsInfo.getDev();
    }

}