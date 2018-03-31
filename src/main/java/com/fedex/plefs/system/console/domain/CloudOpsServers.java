package com.fedex.plefs.system.console.domain;

import com.fedex.plefs.system.console.domain.server.Server;
import com.fedex.plefs.system.console.domain.server.ServerConfiguration;
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
public class CloudOpsServers {

    private Map<String, Server> allServers;

    private List<Server> dev;
    private List<Server> test;
    private List<Server> prod;

    final Logger logger = LogManager.getLogger();

    @Autowired
    public CloudOpsServers(ServerConfiguration confInfo) {
        this.allServers = new HashMap<>();
        this.dev = new ArrayList<>();
        this.test = new ArrayList<>();
        this.prod = new ArrayList<>();

        for(Server s : confInfo.getServer()) {

            allServers.put(s.getAlias(), s);
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

    public List<Server> getAllServers() {
        return new ArrayList<>(this.allServers.values());
    }

    public boolean isServerAliasUnknown(String alias) {
        return !this.allServers.containsKey(alias);
    }
}
