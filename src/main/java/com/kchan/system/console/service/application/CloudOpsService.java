package com.kchan.system.console.service.application;

import com.kchan.system.console.service.application.properties.Application;
import com.kchan.system.console.service.application.dto.AppInstanceList;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class CloudOpsService {

    private Map<String, Application> allInstances;

    private List<Application> dev;
    private List<Application> test;
    private List<Application> prod;

    final Logger LOGGER = LogManager.getLogger();

    @Autowired
    public CloudOpsService(AppInstanceList confInfo) {
        this.allInstances = new HashMap<>();
        this.dev = new ArrayList<>();
        this.test = new ArrayList<>();
        this.prod = new ArrayList<>();

        for(Application s : confInfo.getApplication()) {

            allInstances.put(s.getAlias(), s);
            switch (s.getType().toUpperCase()) {
                case "DEV":     dev.add(s);
                                break;
                case "TEST":    test.add(s);
                                break;
                case "PROD":    prod.add(s);
                                break;
                default:        break;
            }
        }
    }

    public List<Application> getAllInstances() {
        return new ArrayList<>(this.allInstances.values());
    }

    public boolean isAliasUnknown(String alias) {
        return !this.allInstances.containsKey(alias);
    }

}
