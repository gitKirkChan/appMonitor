package com.kchan.system.console.service.application.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
public class AppGroup {

    @Getter
    private String name;
    private List<Instance> instance;

    public List<Instance> getInstances() {
        return this.instance;
    }
}
