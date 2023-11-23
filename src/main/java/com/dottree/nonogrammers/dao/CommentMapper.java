package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Post;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mapping(target = "postId", source = "comment.post.postId")
    @Mapping(target = "nickName", source = "comment.userNickName.nickName")  // 다른 테이블에 있는 속성 매핑
    @Mapping(target = "imgSrc", source = "comment.userImgSrc.profileImgUrl")  // 다른 테이블에 있는 속성 매핑
    CommentDTO commentToCommentDTO(Comment comment);
}
