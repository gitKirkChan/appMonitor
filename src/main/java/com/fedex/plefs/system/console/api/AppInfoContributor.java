package com.fedex.plefs.system.console.api;

import com.fedex.plefs.system.console.domain.ApplicationProperties;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(ApplicationProperties.class)
public class AppInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {

    }
}
