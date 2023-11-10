package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@ToString
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String townName;

//    @Builder
//    public User(Long userId, String nickName) {
//        this.userId = userId;
//        this.nickName = nickName;
//    }
}

