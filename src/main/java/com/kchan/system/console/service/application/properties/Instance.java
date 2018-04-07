package com.kchan.system.console.service.application.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
    Used to map and expose the metadata of CloudOps applications defined in the application.yml
 */
@ToString
@Getter @Setter
public class Instance {

    private String level;
    private String type;
    private String hostname;
    private String actuator;
    private String alias;
}
