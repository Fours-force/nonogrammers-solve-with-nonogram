package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {
    private int fileId;
    private int postId;
    private String filename;
    private String fileExtension;
    private String fileUrl;
    private String createdAt;
    private int statusCode;
}
