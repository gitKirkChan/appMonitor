package com.kchan.system.console.api.entity;

import com.kchan.system.console.service.application.dto.Health;
import com.kchan.system.console.service.application.properties.Instance;
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

    public AppHealth(Instance instance, Health health) {
        this.appName = instance.getAlias();
        this.hostname = instance.getHostname();
        this.status = health.getStatus();
    }
}
