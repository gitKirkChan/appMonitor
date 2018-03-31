package com.fedex.plefs.system.console.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("cloudops.server")
public class CloudOpsProperties {

    @Getter @Setter
    private List<Dev> dev;
    @Getter @Setter
    private List<Test> test;

    @ToString @Getter @Setter
    public static class Dev {
        private String level;
        private String hostname;
        private String alias;
    }

    @ToString @Getter @Setter
    public static class Test {
        private String level;
        private String hostname;
        private String alias;
    }
}
