package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSolvedCountDTO {
    private int userId;
    private int solvedCount;
}
