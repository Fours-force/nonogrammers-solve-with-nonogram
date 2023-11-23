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
//    private int postId;
    private int userId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User userNickName;
    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User userImgSrc;
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
    public int getPostId() {
        return post != null ? post.getPostId() : 0; // 적절한 기본값 사용
    }
}
