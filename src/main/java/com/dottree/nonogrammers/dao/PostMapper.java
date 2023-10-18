package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PostMapper {
    @Select("SELECT postId, boardType, userId, title, content, createdAt, updatedAt  FROM post WHERE userId = #{userId}")
    public List<PostDTO> selectPostList(int userId);
}
