package com.kchan.system.console.service;

import com.kchan.system.console.domain.server.Server;
import lombok.Getter;

public class ServerStatus {

    @Getter
    private String name;
    @Getter
    private String hostname;
    @Getter
    private String status;

    public ServerStatus(Server server, String status) {
        this.name = server.getAlias();
        this.hostname = server.getHostname();
        this.status = status;
    }
}