package com.kchan.system.console.api;

import com.kchan.system.console.service.application.AppService;
import com.kchan.system.console.api.entity.AppHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AppMonitorController {

    private final AppService appService;

    @Autowired
    public AppMonitorController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping("/system/health")
    @ResponseBody
    public List<AppHealth> allServerHealthCheck() {
        return appService.showAllStatus();
    }

    @RequestMapping("/system/health/down")
    @ResponseBody
    public List<AppHealth> downServerHealthCheck() {
        return appService.showDownStatus();
    }
}
