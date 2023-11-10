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
@Table(name = "userdot")
public class UserDot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userDotId;
    @ManyToOne
    @JoinColumn(name = "nonoId")
    private Nono nono;
    @ManyToOne
    @JoinColumn(name = "dotId")
    private Dot dot;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public UserDot() {

    }
}
