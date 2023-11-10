package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
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
