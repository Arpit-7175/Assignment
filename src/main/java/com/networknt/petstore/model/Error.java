package com.networknt.petstore.model;

import lombok.Data;

@Data
public class Error {
    private String message;
    private Integer code;
}