package com.dottree.nonogrammers.entity;

import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@ToString
@Getter
//@Setter
@Entity
@Table(name = "file")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private String filename;
    private String fileExtension;
    private String fileUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private int statusCode;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private Post post;
    public FileDTO toDto() {
        return FileDTO.builder()
                .fileId(fileId)
                .filename(filename)
                .fileExtension(fileExtension)
                .fileUrl(fileUrl)
                .createdAt(createdAt)
                .statusCode(statusCode)
                .postId(post.getPostId())
                .build();
    }
}
