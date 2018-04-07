package com.kchan.system.console.service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
    Class used to map JSON to Java of HTTP responses
 */
@Getter @Setter
@AllArgsConstructor
public class Health {

    String status;
}