package com.dottree.nonogrammers.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class PostDTO {
    private int id;
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
    private String fileUrls;
    public List<String> getFileUrls() {
        if (fileUrls != null && !fileUrls.isEmpty()) {
            return Arrays.asList(fileUrls.split(","));
        } else {
            return Collections.emptyList();
        }
    }
}


