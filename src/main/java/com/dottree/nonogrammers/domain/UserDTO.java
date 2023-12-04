package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String townName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime changedAt;
    private Integer statusCode;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .email(email)
                .nickName(nickName)
                .profileImgUrl(profileImgUrl)
                .statusCode(statusCode)
                .baekjoonUserId(baekjoonUserId)
                .changedAt(changedAt)
                .build();
    }
}
