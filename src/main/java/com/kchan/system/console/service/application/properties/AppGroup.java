package com.kchan.system.console.service.application.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AppGroup {

    @Getter @Setter
    private String name;
    @Setter
    private List<Instance> instance;

    public List<Instance> getInstances() {
        return this.instance;
    }
}
