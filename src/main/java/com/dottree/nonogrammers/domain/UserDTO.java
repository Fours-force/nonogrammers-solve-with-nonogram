package com.dottree.nonogrammers.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String email;
    private String password;
    private String nickName;
    private String profileImgUrl;
    private String baekjoonUserId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate changedAt;
    private Integer statusCode;

}
