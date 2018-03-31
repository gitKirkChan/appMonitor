package com.fedex.plefs.system.console.domain.server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("cloudops")
public class ServerConfiguration {

    @Getter @Setter
    private List<Server> server;
}

