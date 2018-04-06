package com.kchan.system.console.service.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpError extends RuntimeException {

    private String code;
    private String error;
}
