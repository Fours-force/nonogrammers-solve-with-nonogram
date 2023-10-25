package com.dottree.nonogrammers.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UploadImageVO {
    private MultipartFile[] uploadImageFiles;
}

