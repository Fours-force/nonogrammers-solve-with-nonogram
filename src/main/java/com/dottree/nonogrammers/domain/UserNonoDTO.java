package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNonoDTO {
    private int userId;
    private int nonoId;
    private int isSolved;
    private String createdAt;
}
