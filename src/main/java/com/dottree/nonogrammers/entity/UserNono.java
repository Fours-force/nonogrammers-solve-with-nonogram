package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "usernono")
public class UserNono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNonoId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "nonoId")
    private Nono nono;
    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    private Integer isSolved;

    public UserNono() {

    }

    public void update(Integer isSolved){
        this.isSolved = isSolved;
    }
}
