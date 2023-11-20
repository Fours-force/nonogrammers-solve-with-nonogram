package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.Post;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
//@Entity
//@Setter
//@Getter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private int postId;
    private int boardType;
    private String title;
    private String content;
    private int userId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private long commentCount;
    private long likeCount;
    private int viewCount;
    private String nickName;
    private String imgSrc;
    private String fileUrls; // 수정할 부분
    private String boardTypeStr;

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .userId(userId)
                .boardType(boardType)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .viewCount(viewCount)
                .build();
    }
//    public List<String> getFileUrls() {
//        if (fileUrls != null && !fileUrls.isEmpty()) {
//            return Arrays.asList(fileUrls.split(","));
//        } else {
//            return Collections.emptyList();
//        }
//    }
}


