package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class DotDTO {
    private int dotId;
    private int nonoId;
    private String color;
}
