package com.kchan.system.console.service.application.dto;

import com.kchan.system.console.service.application.properties.AppGroup;
import com.kchan.system.console.service.application.properties.ApplicationAccounts;
import com.kchan.system.console.service.application.properties.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CloudOpsAccounts {

    private final ApplicationAccounts applicationAccounts;
    private HashMap<String, List<Instance>> appAccounts = new HashMap();

    @Autowired
    public CloudOpsAccounts(ApplicationAccounts applicationProperties) {
        this.applicationAccounts = applicationProperties;

        for (AppGroup appGroup : applicationProperties.getAppAccounts())
            this.appAccounts.put(appGroup.getName(), appGroup.getInstances());
    }

    public List<AppGroup> getAllAppGroups() {
        return this.applicationAccounts.getAppAccounts();
    }

    public List<Instance> getAllAppInstances() {
        return new ArrayList(appAccounts.values());
    }

    public List<Instance> getAppInstancesFor(String appName) {

        if (this.isUnknownApp(appName)) {
            return new ArrayList<>();
        }

        return appAccounts.get(appName);
    }

    public boolean isUnknownApp(String appName) {
        return !this.appAccounts.containsKey(appName);
    }
}
