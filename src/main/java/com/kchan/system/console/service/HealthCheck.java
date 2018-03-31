package com.kchan.system.console.service;

import com.kchan.system.console.domain.CloudOpsServers;
import com.kchan.system.console.domain.server.Server;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheck {

    @Autowired
    private CloudOpsServers cloudOpsInfo;

    public List<Status> showAllStatus() {
        return findAllStatus();
    }

    public List<Status> showDownStatus() {
        return findAnyDownStatus();
    }

    private List<Status> findAllStatus() {
        List<Status> response = new ArrayList<>();
        List<Server> serversToCheck = cloudOpsInfo.getAllServers();

        serversToCheck.forEach( server -> {
            response.add(checkServerStatus(server));
        });

        return response;
    }

    private List<Status> findAnyDownStatus() {
        List<Status> response = new ArrayList<>();
        List<Server> serversToCheck = cloudOpsInfo.getAllServers();

            serversToCheck.forEach( server -> {
                Status status = checkServerStatus(server);

                if (status.getStatus().equalsIgnoreCase("DOWN"))
                    response.add(status);
        });

        return response;
    }

    private Status checkServerStatus(Server server) {
        /*
         * Crude hardcoded logic for demonstration purposes of application health monitors
         * */
        if (cloudOpsInfo.isServerAliasUnknown(server.getAlias())) {
            return new Status(server, "UNKNOWN SERVER; STATUS UNKNOWN");
        }
        else if (server.getHostname().contains("2")) {
            return new Status(server, "DOWN");
        }
        else {
            return new Status(server, "UP");
        }
    }

    public static class Status {

        @Getter
        private String name;
        @Getter
        private String hostname;
        @Getter
        private String status;

        public Status(Server server, String status) {
            this.name = server.getAlias();
            this.hostname = server.getHostname();
            this.status = status;
        }
    }
}
