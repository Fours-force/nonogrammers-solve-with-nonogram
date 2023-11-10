package com.dottree.nonogrammers.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(columnDefinition = "text")
    private String profileImgUrl;

    @ColumnDefault("1")
    private int statusCode;

    @Column(nullable = false, unique = true)
    private String baekjoonUserId;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime changedAt;

    private String townName;

    @Builder
    public User(String email, String password, String nickName, String baekjoonUserId) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.baekjoonUserId = baekjoonUserId;
        this.profileImgUrl = "/images/default.png";
        this.statusCode = 1;
    }
}
