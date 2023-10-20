package com.dottree.nonogrammers.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    private int commentCount;
    private int likeCount;
    private int viewCount;
    private String boardTypeStr;
    private String nickName;
    private String imgSrc;
}


