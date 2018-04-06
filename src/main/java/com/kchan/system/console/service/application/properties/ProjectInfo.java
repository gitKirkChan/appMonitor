package com.kchan.system.console.service.application.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ProjectInfo {

    @Getter @Setter
    private Project project;

    @ToString
    @Getter @Setter
    public static class Project {
        private String name;
        private String version;
        private String java;

        private Env env;
    }

    @ToString
    @Getter @Setter
    public static class Env {
        private String level;
        private String type;
    }
}
