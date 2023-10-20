package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc FROM post AS p INNER JOIN user AS u ON p.userId = u.userId ORDER BY p.createdAt DESC")
    public List<PostDTO> listm();
    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName FROM post AS p JOIN user AS u ON p.userId = u.userId where p.title like concat('%',#{keyword},'%') ORDER BY p.createdAt DESC")
    public List<PostDTO> search(String keyword);
    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=1 ORDER BY p.createdAt DESC")
    public List<PostDTO> listN();
    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=2 ORDER BY p.createdAt DESC")
    public List<PostDTO> listF();
    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=3 ORDER BY p.createdAt DESC")
    public List<PostDTO> listQ();
    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, b.boardName as boardTypeStr, u.nickName as nickName FROM post AS p JOIN board AS b ON p.boardType = b.boardType JOIN user AS u ON p.userId = u.userId WHERE p.postId = #{postId}")
    public PostDTO detailss(String postId);
    @Select("SELECT c.commentId, c.postId, c.userId, c.content, c.createdAt, c.updatedAt, u.nickName as nickName FROM comment AS c JOIN user AS u ON c.userId = u.userId WHERE c.postId = #{postId}")
    public List<CommentDTO> commList(String postId);
    @Select("SELECT count(*) as countC FROM comment WHERE postId =#{postId} GROUP BY postId")
    public List<Integer> counting(String postId);
    @Insert("insert into comment (postId,userId,content) values (#{postId},#{userId},#{content})")
    public boolean insertComm(CommentDTO cd);
    @Update("UPDATE post SET viewCount = viewCount + 1 WHERE postId = #{postId}")
    public void incrementViewCount(String postId);
    @Select("SELECT postId, boardType, userId, title, content, createdAt, updatedAt  FROM post WHERE userId = #{userId}")
    public List<PostDTO> selectPostList(int userId);

}
