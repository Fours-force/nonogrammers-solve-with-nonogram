package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

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
    @OneToMany
    @JoinColumn(name = "postId")
    private List<Comment> comment;
    @OneToMany
    @JoinColumn(name = "postId")
    private List<Likes> likes;
    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User userNickName;
    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User userImgSrc;

    @ManyToOne
    @JoinColumn(name = "boardType", insertable = false, updatable = false)
    private Board board;
}
