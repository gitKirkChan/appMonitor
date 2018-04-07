package com.kchan.system.console.api.entity;

import com.kchan.system.console.service.application.dto.Health;
import com.kchan.system.console.service.application.properties.Application;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
    Class to define API responses
 */
@Getter
@AllArgsConstructor
public class AppHealth {

    private String appName;
    private String hostname;
    private String status;

    public AppHealth(Application application, Health health) {
        this.appName = application.getAlias();
        this.hostname = application.getHostname();
        this.status = health.getStatus();
    }
}
