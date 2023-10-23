package com.dottree.nonogrammers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserPostVO {
    private int userId;
    private UserDTO userDTO;
    private List<PostDTO> userPostList;
}
