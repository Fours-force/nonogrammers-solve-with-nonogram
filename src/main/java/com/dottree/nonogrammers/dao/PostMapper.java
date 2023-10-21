package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select postId,boardType,title,content,userId,createdAt from post")
    public List<PostDTO> listm();
    @Select("select postId,boardType,title,content,userId,createdAt from post where title like concat('%',#{keyword},'%')")
    public List<PostDTO> search(String keyword);
    @Select("select postId,boardType,title,content,userId,createdAt from post where boardType=1")
    public List<PostDTO> listN();
    @Select("select postId,boardType,title,content,userId,createdAt from post where boardType=2")
    public List<PostDTO> listF();
    @Select("select postId,boardType,title,content,userId,createdAt from post where boardType=3")
    public List<PostDTO> listQ();
    @Select("select postId,boardType,title,content,userId,createdAt from post where postId=#{postId}")
    public PostDTO detailss(String postId);
    @Select("select commentId,postId,userId,content,createdAt,updatedAt from comment where postId=#{postId}")
    public List<CommentDTO> commList(String postId);
    @Insert("insert into comment (content) values (#{content})")
    public boolean insertComm(CommentDTO cd);

    /**
     * 유저의 게시글 목록 보여주기
     * @param userId
     * @return List<PostDTO>
     */
    @Select("SELECT postId, boardType, userId, title, content, createdAt, updatedAt  FROM post WHERE userId = #{userId}")
    public List<PostDTO> selectPostList(int userId);

    /**
     * 유저의 게시글 수정하기
     * @param title
     * @param content
     * @param postId
     * @param userId
     * @return boolean
     */
    @Update("update post set title = #{title}, content = #{content}, updatedAt = now() where postId = #{postId} and userId = #{userId}")
    public boolean updatePostByPostIdAndUserId(@Param("title") String title, @Param("content") String content, @Param("postId") int postId, @Param("userId") int userId);

}
