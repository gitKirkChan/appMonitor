package com.kchan.system.console.service.application.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ToString
@Getter @Setter
public class Server {

    private String level;
    private String type;
    private String hostname;
    private String actuator;
    private String alias;
}
