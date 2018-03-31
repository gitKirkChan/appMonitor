package com.fedex.plefs.system.console.api;

import com.fedex.plefs.system.console.service.HealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.fedex.plefs.system.console.service.HealthCheck.*;

@Controller
public class ServerMonitorController {

    private final HealthCheck healthCheck;

    @Autowired
    public ServerMonitorController(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    @RequestMapping("/status/health/all")
    @ResponseBody
    public List<Status> allServerHealthCheck() {
        return healthCheck.showAllStatus();
    }

    @RequestMapping("/status/health/down")
    @ResponseBody
    public List<Status> downServerHealthCheck() {
        return healthCheck.showDownStatus();
    }
}
