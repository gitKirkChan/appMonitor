package com.kchan.system.console.service.application.dto;

import com.kchan.system.console.service.application.properties.AppGroup;
import com.kchan.system.console.service.application.properties.ApplicationAccounts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@Getter
public class CloudOpsAccounts {

    private final HashMap applicationInstances;

    @Autowired
    public CloudOpsAccounts(ApplicationAccounts applicationProperties) {
        applicationInstances = new HashMap();
        for (AppGroup appGroup : applicationProperties.getApplicationAccountsList())
            this.applicationInstances.put(appGroup.getName(), appGroup.getInstances());
    }
}
