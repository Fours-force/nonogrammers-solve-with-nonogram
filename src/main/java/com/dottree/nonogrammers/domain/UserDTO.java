package com.dottree.nonogrammers.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String email;
    private String password;
    private String nickName;
    private String profileImgUrl;
    private String baekjoonUserId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate changedAt;
}
