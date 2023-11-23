package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.Likes;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {
    private int userId;
    private int postId;
    public Likes toEntity(User user, Post post) {
        return Likes.builder()
                .user(user)
                .post(post)
                .build();
    }
}
