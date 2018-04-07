package com.kchan.system.console.service.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kchan.system.console.service.application.dto.Health;
import com.kchan.system.console.service.application.properties.Application;
import com.kchan.system.console.properties.ProjectInfo;
import com.kchan.system.console.api.entity.AppHealth;
import com.kchan.system.console.service.http.GetRequest;
import com.kchan.system.console.service.http.HttpError;
import com.kchan.system.console.service.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    private final static Logger LOGGER = LogManager.getLogger();

    private final CloudOpsService cloudOpsInfo;
    private final GetRequest http;

    @Autowired
    public AppService(CloudOpsService cloudOpsInfo, GetRequest http) {
        this.cloudOpsInfo = cloudOpsInfo;
        this.http = http;
    }

    public List<AppHealth> showAllStatus() {
        List<AppHealth> response = new ArrayList<>();
        List<Application> appsToCheck = cloudOpsInfo.getAllInstances();

        appsToCheck.forEach( instances -> {
            Health health = checkHealth(instances);
            response.add(new AppHealth(instances, health));
        });

        return response;
    }

    public List<AppHealth> showDownStatus() {
        List<AppHealth> response = new ArrayList<>();
        List<Application> appsToCheck = cloudOpsInfo.getAllInstances();

        appsToCheck.forEach( instance -> {
            Health health = checkHealth(instance);

            if (!health.getStatus().equalsIgnoreCase("UP")) {
                response.add(new AppHealth(instance, health));
            }
        });

        return response;
    }

    private Health checkHealth(Application application) {
        if (cloudOpsInfo.isAliasUnknown(application.getAlias())) {
            return new Health("Incorrect application information");
        }
        // Real health checks
        else {
            return sendHealthRequest(application);
        }
    }

    private Health sendHealthRequest(Application application) {

        String actuatorHealthUrl = String.format("%s/actuator/health", application.getActuator());
        HttpResponse response;

        try {
            response = http.call(actuatorHealthUrl);

            ObjectMapper om = new ObjectMapper();
            // Use of object node because of deserialization issues when mapping to our Health class
            ObjectNode status = om.readValue(response.getMessage(), ObjectNode.class);
            return new Health(status.get("status").asText());
        } catch (HttpError e) {
            return new Health(e.getError());
        } catch (IOException e) {
            LOGGER.error("Failed to map HTTP response to Java object [Health]");
            return new Health("SYSTEM ERROR");
        }
    }

    private ProjectInfo getInfo(Application application) {

        String actuatorInfoUrl = String.format("%s/actuator/info", application.getHostname());
        HttpResponse response = http.call(actuatorInfoUrl);
        ProjectInfo projectInfo;

        if(response.getCode().equals("200")) {

            try {
                ObjectMapper om = new ObjectMapper();
                projectInfo = om.readValue(response.getMessage(), ProjectInfo.class);

                return projectInfo;
            } catch (IOException e) {
                LOGGER.warn(String.format("Failed to map HTTP response to class [%s]", ProjectInfo.class));
                // TODO Need a runtime exception
                return new ProjectInfo();
            }
        }

        return new ProjectInfo();
    }
}
