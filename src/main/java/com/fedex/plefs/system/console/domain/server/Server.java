package com.fedex.plefs.system.console.domain.server;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class Server {

    private String level;
    private String type;
    private String hostname;
    private String alias;
}
