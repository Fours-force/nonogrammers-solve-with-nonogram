package com.dottree.nonogrammers.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
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

    private String roles;

    @Builder
    public User(String email, String password, String nickName, String baekjoonUserId, String roles) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.baekjoonUserId = baekjoonUserId;
        this.roles = roles;
        this.profileImgUrl = "/images/default.png";
        this.statusCode = 1;
    }

    @Override
    public String getUsername() {return email;}

    @Override
    public String getPassword() {return password;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }
}
