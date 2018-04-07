package com.kchan.system.console.api;

import com.kchan.system.console.properties.ProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppInfoContributor implements InfoContributor {

    private ProjectInfo projectInfo;

    @Autowired
    public AppInfoContributor(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @Override
    public void contribute(Info.Builder builder) {

        Map<String, String> projectProps = new HashMap<>();
        projectProps.put("name", projectInfo.getProject().getName());
        projectProps.put("version", projectInfo.getProject().getVersion());

        Map<String, String> envProps = new HashMap<>();
        envProps.put("level", projectInfo.getProject().getEnv().getLevel());
        envProps.put("type", projectInfo.getProject().getEnv().getType());

        builder.withDetail("project", projectProps);
        builder.withDetail("java", projectInfo.getProject().getJava());
        builder.withDetail("env", envProps);
    }
}
