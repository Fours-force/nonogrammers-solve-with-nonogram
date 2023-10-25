package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserNonoVO {
    private int userNonoId;
    private int nonoId;
    private int userId;
    private String nonoImgUrl;
    private int isSolved;
    private int levelType;
    private LocalDate createdAt;
}
