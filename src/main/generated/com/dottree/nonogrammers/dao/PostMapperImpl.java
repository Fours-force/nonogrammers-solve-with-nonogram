package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.Board;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T09:40:01+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setNickName( postUserNickNameNickName( post ) );
        postDTO.setImgSrc( postUserImgSrcProfileImgUrl( post ) );
        postDTO.setBoardTypeStr( postBoardBoardName( post ) );
        postDTO.setPostId( post.getPostId() );
        postDTO.setBoardType( post.getBoardType() );
        postDTO.setTitle( post.getTitle() );
        postDTO.setContent( post.getContent() );
        postDTO.setUserId( post.getUserId() );
        postDTO.setCreatedAt( post.getCreatedAt() );
        postDTO.setUpdatedAt( post.getUpdatedAt() );
        postDTO.setViewCount( post.getViewCount() );

        postDTO.setCommentCount( getCommentCount(post) );
        postDTO.setLikeCount( getLikeCount(post) );

        return postDTO;
    }

    private String postUserNickNameNickName(Post post) {
        if ( post == null ) {
            return null;
        }
        User userNickName = post.getUserNickName();
        if ( userNickName == null ) {
            return null;
        }
        String nickName = userNickName.getNickName();
        if ( nickName == null ) {
            return null;
        }
        return nickName;
    }

    private String postUserImgSrcProfileImgUrl(Post post) {
        if ( post == null ) {
            return null;
        }
        User userImgSrc = post.getUserImgSrc();
        if ( userImgSrc == null ) {
            return null;
        }
        String profileImgUrl = userImgSrc.getProfileImgUrl();
        if ( profileImgUrl == null ) {
            return null;
        }
        return profileImgUrl;
    }

    private String postBoardBoardName(Post post) {
        if ( post == null ) {
            return null;
        }
        Board board = post.getBoard();
        if ( board == null ) {
            return null;
        }
        String boardName = board.getBoardName();
        if ( boardName == null ) {
            return null;
        }
        return boardName;
    }
}
