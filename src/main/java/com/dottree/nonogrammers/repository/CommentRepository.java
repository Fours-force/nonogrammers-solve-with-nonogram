package com.dottree.nonogrammers.repository;
import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
//    @Query("SELECT count(*) as countC FROM comment c WHERE c.postId =:postId GROUP BY c.postId")
    public List<Integer> countByPostId(@Param("postId") int postId); //counting
    @Transactional
    @Modifying
    @Query("insert into Comment (postId,userId,content) values (:postId,:userId,:content)")
    public void insertComment(@Param("postId") int postId, @Param("userId") int userId, @Param("content") String content); //insertComm
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Comment c WHERE c.commentId = :commentId")
//    public void deleteComment(@Param("commentId") int commentId); //deleteComm
    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.content = :content, c.updatedAt = now() WHERE c.commentId = :commentId")
    public void editComment(@Param("content") String content,@Param("commentId") int commentId); //editComm
//    @Query("SELECT c.commentId AS commentId, c.postId AS postId, c.userId AS userId, c.content AS content, c.createdAt AS createdAt, c.updatedAt AS updatedAt, u.nickName AS nickName, u.profileImgUrl AS imgSrc) " +
//            "FROM Comment c " +
//            "JOIN User u ON c.userId = u.userId " +
//            "WHERE c.postId = :postId")
//    List<CommentDTO> commentList(@Param("postId") int postId);
    List<Comment> findByPostIdOrderByCreatedAt(int postId);

    public void deleteByCommentId(int commentId);
}
