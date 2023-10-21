package com.dottree.nonogrammers.domain;

import lombok.*;

import java.time.LocalDate;

@Data
public class UserDTO {
    private int userId;
    private String email;
    private String password;
    private String nickName;
    private String profileImgUrl;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate changedAt;
}
