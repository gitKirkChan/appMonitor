package com.kchan.system.console.service.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kchan.system.console.service.application.dto.Health;
import com.kchan.system.console.service.application.properties.Server;
import com.kchan.system.console.service.application.properties.ProjectInfo;
import com.kchan.system.console.api.entity.AppHealth;
import com.kchan.system.console.service.http.GetRequest;
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

    private final CloudOpsServers cloudOpsInfo;
    private final GetRequest http;

    @Autowired
    public AppService(CloudOpsServers cloudOpsInfo, GetRequest http) {
        this.cloudOpsInfo = cloudOpsInfo;
        this.http = http;
    }

    public List<AppHealth> showAllStatus() {
        List<AppHealth> response = new ArrayList<>();
        List<Server> serversToCheck = cloudOpsInfo.getAllServers();

        serversToCheck.forEach( server -> {
            Health health = checkServerStatus(server);
            response.add(new AppHealth(server, health));
        });

        return response;
    }

    public List<AppHealth> showDownStatus() {
        List<AppHealth> response = new ArrayList<>();
        List<Server> serversToCheck = cloudOpsInfo.getAllServers();

        serversToCheck.forEach( server -> {
            Health health = checkServerStatus(server);

            if (!health.getStatus().equalsIgnoreCase("UP")) {
                response.add(new AppHealth(server, health));
            }
        });

        return response;
    }

    private Health checkServerStatus(Server server) {
        if (cloudOpsInfo.isServerAliasUnknown(server.getAlias())) {
            return new Health("Incorrect server information");
        }
        // Real health checks
        else {
            return sendHealthRequest(server);
        }
    }

    private Health sendHealthRequest(Server server) {

        String actuatorHealthUrl = String.format("%s/actuator/health", server.getActuator());
        HttpResponse response = http.call(actuatorHealthUrl);

        if (response.getCode().equals("200")) {

            try {
                ObjectMapper om = new ObjectMapper();
                LOGGER.info(String.format("Response: [%s]", response.getMessage()));
                // Use of object node because of deserialization issues
                ObjectNode status = om.readValue(response.getMessage(), ObjectNode.class);
                return new Health(status.get("status").asText());
            } catch (IOException e) {
                LOGGER.warn("Failed to map HTTP response to Java object [Health]");
                return new Health("ERROR");
            }
        }

        return new Health(response.getMessage());
    }

    private ProjectInfo getInfo(Server server) {

        String actuatorInfoUrl = String.format("%s/actuator/info", server.getHostname());
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
