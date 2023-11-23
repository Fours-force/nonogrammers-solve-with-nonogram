package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Integer> {
        // 해당 게시글의 좋아요 개수
//    @Select("select count(*) from likes where postId=#{postId}")
//    public int getPostLike(int postId);
    public int countByPost_PostId(int postId);

    // 게시글 좋아요 여부 조회
//    @Select("select count(*) from likes where userId=#{userId} and postId=#{postId}")
//    public int getUserPostLike(LikeDTO dto);
    public int countByUser_UserIdAndPost_PostId(int userId, int postId);
    // 게시글 좋아요 정보 추가
//    @Insert("insert into likes(userId, postId) values(#{userId}, #{postId})")
//    public boolean addPostLike(LikeDTO dto);

    // 게시글 좋아요 정보 삭제
//    @Delete("delete from likes where userId=#{userId} and postId=#{postId}")
//    public boolean delPostLike(LikeDTO dto);
    public void deleteByPost_PostId(int postId);
}
