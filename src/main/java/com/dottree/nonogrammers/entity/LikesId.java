package com.dottree.nonogrammers.entity;

import java.io.Serializable;
import java.util.Objects;

public class LikesId implements Serializable {

    private User user;
    private Post post;

    // 생성자
    public LikesId() {
    }

    public LikesId(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
