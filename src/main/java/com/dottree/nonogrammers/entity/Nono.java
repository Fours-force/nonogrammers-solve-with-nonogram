package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "nono")
@Builder(toBuilder = true)
@AllArgsConstructor
public class Nono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nonoId;
    private int levelType;
    private String nonoImgUrl;
    private String allProblemToStr;

    public Nono() {

    }
}
