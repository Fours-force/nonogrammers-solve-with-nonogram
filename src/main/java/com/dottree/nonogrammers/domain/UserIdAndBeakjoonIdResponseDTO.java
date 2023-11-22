package com.dottree.nonogrammers.domain;

import com.dottree.nonogrammers.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserIdAndBeakjoonIdResponseDTO {
    private int userId;
    private String baekjoonId;

    public UserIdAndBeakjoonIdResponseDTO(User user){
        this.userId = user.getUserId();
        this.baekjoonId = user.getBaekjoonUserId();
    }
}
