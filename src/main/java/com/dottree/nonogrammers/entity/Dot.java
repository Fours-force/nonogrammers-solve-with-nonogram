package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "dot")
@Builder(toBuilder = true)
@AllArgsConstructor
public class Dot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dotId;
    @ManyToOne
    @JoinColumn(name = "nonoId")
    private Nono nono;
    private String color;

    public Dot() {

    }
}
