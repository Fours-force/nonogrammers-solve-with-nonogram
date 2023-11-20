package com.dottree.nonogrammers.domain;

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
}
