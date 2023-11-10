package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Entity
@Table(name = "nono")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Nono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nonoId;
    private int levelType;
    private String nonoImgUrl;
    private String allProblemToStr;
}
