package com.kchan.system.console.service.application.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kchan.system.console.api.entity.AppHealth;
import com.kchan.system.console.service.application.dto.CloudOpsAccounts;
import com.kchan.system.console.service.application.dto.Health;
import com.kchan.system.console.service.application.properties.AppGroup;
import com.kchan.system.console.service.application.properties.Instance;
import com.kchan.system.console.service.http.GetRequest;
import com.kchan.system.console.service.http.HttpError;
import com.kchan.system.console.service.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HealthCheck {

    private final static Logger LOGGER = LogManager.getLogger();

    private final CloudOpsAccounts apps;
    private final GetRequest http;

    @Autowired
    public HealthCheck(CloudOpsAccounts apps, GetRequest http) {
        this.apps = apps;
        this.http = http;
    }

    public List<AppHealth> checkApps(List<AppGroup> appGroups) {

        List<AppHealth> response = new ArrayList<>();
        appGroups.forEach( appGroup -> {
            List<AppHealth> appHealths = this.checkApp(appGroup);
            response.addAll(appHealths);
        });

        return response;
    }

    public List<AppHealth> checkApp(AppGroup appGroup) {

        List<AppHealth> response = new ArrayList<>();
        appGroup.getInstances().forEach( instance -> {
            Health health = this.checkHealth(instance);
            response.add(new AppHealth(instance, health));
        });

        return response;
    }

    private Health checkHealth(Instance instance) {
        return sendHealthRequest(instance);
    }

    private Health sendHealthRequest(Instance application) {

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
}
