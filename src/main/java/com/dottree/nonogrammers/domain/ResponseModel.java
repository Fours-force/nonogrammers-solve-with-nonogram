package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
public class ResponseModel {
    private Integer statusCode;
    private String title;
    private String data;
    private String message;
    private HashMap<String, Object> mapData;
}
