package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.domain.PostDTO.PostDTOBuilder;
import com.dottree.nonogrammers.entity.Board;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-23T01:38:11+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.nickName( postUserNickNameNickName( post ) );
        postDTO.imgSrc( postUserImgSrcProfileImgUrl( post ) );
        postDTO.boardTypeStr( postBoardBoardName( post ) );
        postDTO.postId( post.getPostId() );
        postDTO.boardType( post.getBoardType() );
        postDTO.title( post.getTitle() );
        postDTO.content( post.getContent() );
        postDTO.userId( post.getUserId() );
        postDTO.createdAt( post.getCreatedAt() );
        postDTO.updatedAt( post.getUpdatedAt() );
        postDTO.viewCount( post.getViewCount() );

        postDTO.commentCount( getCommentCount(post) );
        postDTO.likeCount( getLikeCount(post) );

        return postDTO.build();
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
