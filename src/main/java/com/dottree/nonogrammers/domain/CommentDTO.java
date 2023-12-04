package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.File;
import com.dottree.nonogrammers.entity.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int commentId;
    private int postId;
    private int userId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String nickName;
    private String imgSrc;
//    public CommentDTO(int postId, int userId, String content){
//        this.postId=postId;
//        this.userId=userId;
//        this.content=content;
//    }
    public Comment toEntity(Post post) {
        return Comment.builder()
                .commentId(commentId)
                .post(post)
                .userId(userId)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
