package com.kchan.system.console.service.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@AllArgsConstructor
public class HttpResponse {

    private String code;
    private String message;
}
