package com.dottree.nonogrammers.domain;

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
//    private String fileUrls; // 수정할 부분
    private String boardTypeStr;
//    public PostDTO(int postId,int boardType,
//    String title,
//    String content,
//    int userId,
//    LocalDate createdAt,
//    LocalDate updatedAt,
//    long commentCount,
//    long likeCount,
//    int viewCount,
//    String nickName,
//    String imgSrc,
//    //    private String fileUrls; // 수정할 부분
//    String boardTypeStr){
//        this.postId=postId;
//        this.boardType=boardType;
//        this.content=content;
//        this.userId=userId;
//        this.createdAt=createdAt;
//        this.updatedAt=updatedAt;
//        this.commentCount=commentCount;
//        this.likeCount=likeCount;
//        this.viewCount=viewCount;
//        this.nickName=nickName;
//        this.imgSrc=imgSrc;
//        this.boardTypeStr=boardTypeStr;
//    }

//    public List<String> getFileUrls() {
//        if (fileUrls != null && !fileUrls.isEmpty()) {
//            return Arrays.asList(fileUrls.split(","));
//        } else {
//            return Collections.emptyList();
//        }
//    }
}


