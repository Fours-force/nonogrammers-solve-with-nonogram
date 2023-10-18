package com.dottree.nonogrammers.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDTO {
    private int postId;
    private int boardType;
    private int userId;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
