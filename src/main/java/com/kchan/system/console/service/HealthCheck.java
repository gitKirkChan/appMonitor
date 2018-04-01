package com.kchan.system.console.service;

import com.kchan.system.console.domain.CloudOpsServers;
import com.kchan.system.console.domain.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheck {

    private final Logger LOGGER;
    private final CloudOpsServers cloudOpsInfo;

    @Autowired
    public HealthCheck(CloudOpsServers cloudOpsInfo) {
        this.cloudOpsInfo = cloudOpsInfo;
        LOGGER = LogManager.getLogger();
    }

    public List<ServerStatus> showAllStatus() {
        return findAllStatus();
    }

    public List<ServerStatus> showDownStatus() {
        return findAnyDownStatus();
    }

    private List<ServerStatus> findAllStatus() {
        List<ServerStatus> response = new ArrayList<>();
        List<Server> serversToCheck = cloudOpsInfo.getAllServers();

        serversToCheck.forEach( server ->
            response.add(checkServerStatus(server))
        );

        return response;
    }

    private List<ServerStatus> findAnyDownStatus() {
        List<ServerStatus> response = new ArrayList<>();
        List<Server> serversToCheck = cloudOpsInfo.getAllServers();

            serversToCheck.forEach( server -> {
                ServerStatus status = checkServerStatus(server);

                if (status.getStatus().equalsIgnoreCase("DOWN"))
                    response.add(status);
        });

        return response;
    }

    private ServerStatus checkServerStatus(Server server) {
        /*
         * Crude hardcoded logic for demonstration purposes of application health monitors
         * */
        if (cloudOpsInfo.isServerAliasUnknown(server.getAlias())) {
            return new ServerStatus(server, "UNKNOWN SERVER; STATUS UNKNOWN");
        }
        // Fake status for fake servers
        else if (server.getHostname().contains("xx")) {

            if (server.getHostname().contains("2")) {
                return new ServerStatus(server, "FAKED DOWN");
            }
            else {
                return new ServerStatus(server, "FAKED UP");
            }
        }
        // Real health checks
        else {
            return sendHealthRequest(server);
        }
    }

    private ServerStatus sendHealthRequest(Server server) {

        String healthStatus = null;

        try {

            URL url = new URL(server.getHostname());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.setRequestProperty("Accept", "application/json");

            if(huc.getResponseCode() != 200) {
                LOGGER.warn(String.format("Failed health check against [%s]; HTTP ServerStatus Code [%s]; Response [%s]",
                        server.getHostname(), huc.getResponseCode(), huc.getResponseMessage()));
                return new ServerStatus(server, "STATUS ERROR");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String response;

            while((response = br.readLine()) != null)  {
                LOGGER.trace(String.format("Health check response: [%s]", response));
                if(response.contains("UP")) {
                    healthStatus = "UP";
                    break;
                }
            }

            huc.disconnect();

        } catch (MalformedURLException e) {
            LOGGER.error(String.format("Bad URL [%s] for health check; cannot get health status for [%s]",
                    server.getHostname(), server.getAlias()));

        } catch (IOException e) {
            LOGGER.error(String.format("Network error: %s", e.toString()));

        }

        if(healthStatus == null) {
            return new ServerStatus(server, "UNKNOWN");
        }
        return new ServerStatus(server, healthStatus);
    }
}
