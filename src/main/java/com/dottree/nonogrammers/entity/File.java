package com.dottree.nonogrammers.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity
@Table(name = "file")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private int postId;
    private String filename;
    private String fileExtension;
    private String fileUrl;
    @CreationTimestamp
    private LocalDateTime createAt;
    private int statusCode;
}
