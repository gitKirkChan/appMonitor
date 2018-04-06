package com.kchan.system.console.api.entity;

import com.kchan.system.console.service.application.dto.Health;
import com.kchan.system.console.service.application.properties.Server;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppHealth {

    private Server server;
    private Health health;
}
