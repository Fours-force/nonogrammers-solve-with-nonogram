package com.dottree.nonogrammers.entity;

import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.domain.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@Table(name = "post")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private int boardType;
    private int userId;
    private String title;
    private String content;
    @CreatedDate
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;
    private int viewCount;

    public void changeTitleAndContentAndUpdatedAt(String title, String content, LocalDate updatedAt){
        this.title = title;
        this.content = content;
        this.updatedAt  = updatedAt;
    }

    public PostDTO toDto() {
        return PostDTO.builder()
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

}
