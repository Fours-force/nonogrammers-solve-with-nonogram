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
@Table(name = "user_solving_row")
public class UserSolvingRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSolvingRowId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "nonoId")
    private Nono nono;
    private int solvingRow;

    public UserSolvingRow() {

    }

    public void update(int solvingRow){
        this.solvingRow = solvingRow;
    }

}
