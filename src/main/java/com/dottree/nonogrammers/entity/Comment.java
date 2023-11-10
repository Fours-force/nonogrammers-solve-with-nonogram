package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@Entity
@Table(name = "comment")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private int postId;
    private int userId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
