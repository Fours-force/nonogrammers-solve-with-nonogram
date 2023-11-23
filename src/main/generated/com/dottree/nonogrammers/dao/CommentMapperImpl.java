package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-23T01:38:12+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setPostId( commentPostPostId( comment ) );
        commentDTO.setNickName( commentUserNickNameNickName( comment ) );
        commentDTO.setImgSrc( commentUserImgSrcProfileImgUrl( comment ) );
        commentDTO.setCommentId( comment.getCommentId() );
        commentDTO.setUserId( comment.getUserId() );
        commentDTO.setContent( comment.getContent() );
        commentDTO.setCreatedAt( comment.getCreatedAt() );
        commentDTO.setUpdatedAt( comment.getUpdatedAt() );

        return commentDTO;
    }

    private int commentPostPostId(Comment comment) {
        if ( comment == null ) {
            return 0;
        }
        Post post = comment.getPost();
        if ( post == null ) {
            return 0;
        }
        int postId = post.getPostId();
        return postId;
    }

    private String commentUserNickNameNickName(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User userNickName = comment.getUserNickName();
        if ( userNickName == null ) {
            return null;
        }
        String nickName = userNickName.getNickName();
        if ( nickName == null ) {
            return null;
        }
        return nickName;
    }

    private String commentUserImgSrcProfileImgUrl(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User userImgSrc = comment.getUserImgSrc();
        if ( userImgSrc == null ) {
            return null;
        }
        String profileImgUrl = userImgSrc.getProfileImgUrl();
        if ( profileImgUrl == null ) {
            return null;
        }
        return profileImgUrl;
    }
}
