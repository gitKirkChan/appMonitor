package com.fedex.plefs.system.console.service;

import com.fedex.plefs.system.console.domain.CloudOpsProperties;
import com.fedex.plefs.system.console.domain.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthCheck {

    @Autowired
    private CloudOpsProperties cloudOpsInfo;

    public List<Server> showServersDown() {
        // TODO Create DOWN servers for this and UP in another method
        return null;
    }
}
