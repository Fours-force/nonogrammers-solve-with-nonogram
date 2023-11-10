package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@Table(name = "post")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private int boardType;
    private int userId;
    private String title;
    private String content;
    @CreatedDate
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;
    private int viewCount;
}
