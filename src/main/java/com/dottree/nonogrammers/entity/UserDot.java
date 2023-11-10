package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userdot")
public class UserDot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userDotId;
//    @ManyToOne
//    @JoinColumn(name = "nonoId")
//    private Nono nono;
//    @ManyToOne
//    @JoinColumn(name = "dotId")
//    private Dot dot;
//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;
    @Column(name = "nonoId")
    private int nonoId;
    @Column(name = "dotId")
    private int dotId;
    @Column(name = "userId")
    private int userId;

}
