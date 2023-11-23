package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter

@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "user_solved_count")
public class UserSolvedCount {
    @Id
    @Column(name = "userId")
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @MapsId
    private User user;
    private int solvedCount;

    public UserSolvedCount() {

    }

    public void update(int solvedCount){
        this.solvedCount = solvedCount;
    }

    @Builder
    public UserSolvedCount(int solvedCount, int userId, User user){
        this.userId = userId;
        this.user = user;
        this.solvedCount = solvedCount;
    }
}
