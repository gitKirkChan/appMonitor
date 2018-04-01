package com.kchan.system.console.api;

import com.kchan.system.console.service.HealthCheck;
import com.kchan.system.console.service.ServerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ServerMonitorController {

    private final HealthCheck healthCheck;

    @Autowired
    public ServerMonitorController(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    @RequestMapping("/status/health/all")
    @ResponseBody
    public List<ServerStatus> allServerHealthCheck() {
        return healthCheck.showAllStatus();
    }

    @RequestMapping("/status/health/down")
    @ResponseBody
    public List<ServerStatus> downServerHealthCheck() {
        return healthCheck.showDownStatus();
    }
}
