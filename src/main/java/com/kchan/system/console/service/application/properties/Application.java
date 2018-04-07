package com.kchan.system.console.service.application.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Used to map and expose the metadata of CloudOps applications defined in the application.yml
 */
@ToString
@Getter @Setter
public class Application {

    private String level;
    private String type;
    private String hostname;
    private String actuator;
    private String alias;
}
