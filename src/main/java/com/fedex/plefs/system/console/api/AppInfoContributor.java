package com.fedex.plefs.system.console.api;

import com.fedex.plefs.system.console.domain.application.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppInfoContributor implements InfoContributor {

    private ApplicationProperties applicationProperties;

    @Autowired
    public AppInfoContributor(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void contribute(Info.Builder builder) {

        Map<String, String> projectProps = new HashMap<>();
        projectProps.put("name", applicationProperties.getProject().getName());
        projectProps.put("version", applicationProperties.getProject().getVersion());

        builder.withDetail("project", projectProps);
        builder.withDetail("java", applicationProperties.getProject().getJava());
    }
}
