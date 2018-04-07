package com.kchan.system.console.service.application.properties;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
    This is the master list of application accounts. Individual accounts contain all remote instances.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("cloudops.application")
@Setter
public class ApplicationAccounts {
    private List<AppGroup> account;

    public List<AppGroup> getAppAccounts() {
        return this.account;
    }
}
