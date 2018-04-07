package com.kchan.system.console.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
    Used to map and expose the metadata of this Spring container properties defined in the application.yml
 */
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
