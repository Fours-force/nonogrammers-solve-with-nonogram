package com.dottree.nonogrammers.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonoDTO {
    private int nonoId;
    private int levelType;
    private String nonoImgUrl;
    private String allProblemToStr;
}
