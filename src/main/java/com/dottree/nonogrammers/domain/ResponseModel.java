package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseModel {
    private Integer statusCode;
    private String title;
    private boolean content;
    private String message;
}
