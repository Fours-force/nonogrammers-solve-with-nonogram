package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    private String email;
    private String nickName;
    private String password;
    private String correctPassword;
    private String profileImgUrl;
    private String baekjoonUserId;
}
