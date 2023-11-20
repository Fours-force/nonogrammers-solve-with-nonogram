package com.dottree.nonogrammers.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private int fileId;
    private int postId;
    private String filename;
    private String fileExtension;
    private String fileUrl;
    private String createdAt;
    private int statusCode;
}
