package com.fedex.plefs.system.console.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationProperties {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Version version;

    @ToString @Getter @Setter
    public static class Version {
        private String java;
        private String project;
        private String test;
    }
}
