package com.kchan.system.console.service.application;

import com.kchan.system.console.service.application.dto.CloudOpsAccounts;
import com.kchan.system.console.service.application.health.HealthCheck;
import com.kchan.system.console.service.application.properties.AppGroup;
import com.kchan.system.console.api.entity.AppHealth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    private final static Logger LOGGER = LogManager.getLogger();

    private final CloudOpsAccounts apps;
    private final HealthCheck healthCheck;

    @Autowired
    public AppService(CloudOpsAccounts apps, HealthCheck healthCheck) {
        this.apps = apps;
        this.healthCheck = healthCheck;
    }

    public List<AppGroup> getAllAppData() {
        return this.apps.getAllAppGroups();
    }

    public List<AppHealth> showAllStatus() {
        return this.healthCheck.checkApps(apps.getAllAppGroups());
    }

    public List<AppHealth> showDownStatus() {
        List<AppHealth> response = new ArrayList<>();
        List<AppHealth> appHealths = this.healthCheck.checkApps(apps.getAllAppGroups());

        appHealths.forEach( hc -> {
            if (!hc.getStatus().equals("UP")) {
                response.add(hc);
            }
        });

        return response;
    }

//    private ProjectInfo getInfo(Application application) {
//
//        String actuatorInfoUrl = String.format("%s/actuator/info", application.getHostname());
//        HttpResponse response = http.call(actuatorInfoUrl);
//        ProjectInfo projectInfo;
//
//        if(response.getCode().equals("200")) {
//
//            try {
//                ObjectMapper om = new ObjectMapper();
//                projectInfo = om.readValue(response.getMessage(), ProjectInfo.class);
//
//                return projectInfo;
//            } catch (IOException e) {
//                LOGGER.warn(String.format("Failed to map HTTP response to class [%s]", ProjectInfo.class));
//                // TODO Need a runtime exception
//                return new ProjectInfo();
//            }
//        }
//
//        return new ProjectInfo();
//    }
}
