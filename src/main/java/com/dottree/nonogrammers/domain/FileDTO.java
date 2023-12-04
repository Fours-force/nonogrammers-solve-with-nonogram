package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.File;
import com.dottree.nonogrammers.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private int fileId;
    private int postId;
    private String filename;
    private String fileExtension;
    private String fileUrl;
    private LocalDateTime createdAt;
    private int statusCode;
    public File toEntity(Post post) {
        return File.builder()
                .fileId(fileId)
                .post(post)
                .filename(filename)
                .fileExtension(fileExtension)
                .fileUrl(fileUrl)
                .createdAt(createdAt)
                .statusCode(statusCode)
                .build();
    }
}
