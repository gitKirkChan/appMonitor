package com.kchan.system.console.service.application.dto;

import com.kchan.system.console.service.application.properties.Application;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
    Used to map metadata of application instances from the application.yml
    OLD
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("cloudops")
public class AppInstanceList {

    @Getter @Setter
    private List<Application> application;
}
