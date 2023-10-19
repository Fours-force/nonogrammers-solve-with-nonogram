package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
@ToString
@Getter
@Setter
public class PostDTO {
    private int postId;
    private int boardType;
    private int userId;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

