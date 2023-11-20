package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.Post;

import org.apache.ibatis.annotations.Options;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {
//    @Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType AS boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, " +
////            "p.createdAt AS updatedAt,"+
//            "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId) AS commentCount, " +
//            "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//            "p.viewCount AS viewCount, " +
//            "u.nickName AS nickName, " +
//            "u.profileImgUrl AS imgSrc, " +
////            "(SELECT f.fileUrl FROM File f WHERE f.postId = p.postId) AS fileUrls," + // 수정된 부분
//            "b.boardName AS boardTypeStr)" +
//            "FROM Post p " +
//            "JOIN User u ON p.userId = u.userId " +
//            "JOIN Board b ON p.boardType = b.boardType " +
////            "JOIN File f ON f.postId = p.postId " +
//            "ORDER BY p.createdAt DESC")
//    List<PostDTO> listAllPosts();
////listm()
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByBoardTypeOrderByCreatedAtDesc(int boardType,Pageable pageable);

    Page<Post> findByTitleContainingOrderByCreatedAtDesc(String keyword,Pageable pageable);
    Optional<Post> findByPostId(int postId);
//
//    @Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType as boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, " +
//            "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId) AS commentCount, " +
//            "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//            "p.viewCount AS viewCount, " +
//            "u.nickName AS nickName, " +
//            "u.profileImgUrl AS imgSrc) " +
////            "(SELECT GROUP_CONCAT(f.fileUrl) FROM File f WHERE f.postId = p.postId)) " +
//            "FROM Post p " +
//            "JOIN User u ON p.userId = u.userId " +
//            "WHERE p.title LIKE %:keyword% " +
//            "ORDER BY p.createdAt DESC")
//    List<PostDTO> searchWord(@Param("keyword") String keyword);//search
//
////    @Query("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=1 ORDER BY p.createdAt DESC")
////    public List<Post> listNoticePosts(); //listN
//@Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType AS boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, " +
//        "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId) AS commentCount, " +
//        "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//        "p.viewCount AS viewCount, " +
//        "u.nickName AS nickName, " +
//        "u.profileImgUrl AS imgSrc, " +
//        "b.boardName AS boardTypeStr)" +
//        "FROM Post p " +
//        "JOIN User u ON p.userId = u.userId " +
//        "JOIN Board b ON p.boardType = b.boardType " +
//        "WHERE p.boardType = 1 " +
//        "GROUP BY p.postId " +
//        "ORDER BY p.createdAt DESC")
//List<PostDTO> listNoticePosts();
//
//    @Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType AS boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, " +
//            "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId) AS commentCount, " +
//            "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//            "p.viewCount AS viewCount, " +
//            "u.nickName AS nickName, " +
//            "u.profileImgUrl AS imgSrc, " +
//            "b.boardName AS boardTypeStr)" +
//            "FROM Post p " +
//            "JOIN User u ON p.userId = u.userId " +
//            "JOIN Board b ON p.boardType = b.boardType " +
//            "WHERE p.boardType = 2 " +
//            "GROUP BY p.postId " +
//            "ORDER BY p.createdAt DESC")
//    public List<PostDTO> listFreePosts();//listF
//
//    @Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType AS boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, " +
//            "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId) AS commentCount, " +
//            "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//            "p.viewCount AS viewCount, " +
//            "u.nickName AS nickName, " +
//            "u.profileImgUrl AS imgSrc, " +
//            "b.boardName AS boardTypeStr)" +
//            "FROM Post p " +
//            "JOIN User u ON p.userId = u.userId " +
//            "JOIN Board b ON p.boardType = b.boardType " +
//            "WHERE p.boardType = 3 " +
//            "GROUP BY p.postId " +
//            "ORDER BY p.createdAt DESC")
//    public List<PostDTO> listQnAPosts(); //listQ
//
//    @Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType AS boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, " +
//            "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId) AS commentCount, " +
//            "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//            "p.viewCount AS viewCount, " +
//            "u.nickName AS nickName, " +
//            "u.profileImgUrl AS imgSrc, " +
//            "b.boardName AS boardTypeStr)" +
//            "FROM Post p " +
//            "JOIN User u ON p.userId = u.userId " +
//            "JOIN Board b ON p.boardType = b.boardType " +
//            "WHERE p.boardType = 4 " +
//            "GROUP BY p.postId " +
//            "ORDER BY p.createdAt DESC")
//    public List<PostDTO> listShowNonoPosts(); //listNN
//
//    @Query("SELECT new com.dottree.nonogrammers.domain.PostDTO(p.postId AS postId, p.boardType AS boardType, p.title AS title, p.content AS content, p.userId AS userId, p.createdAt AS createdAt, p.updatedAt AS updatedAt, " +
//            "(SELECT COUNT(c) FROM Comment c WHERE c.postId = p.postId)  AS commentCount, " +
//            "(SELECT COUNT(l) FROM Likes l WHERE l.postId = p.postId) AS likeCount, " +
//            "p.viewCount AS viewCount, " +
//            "u.nickName AS nickName, " +
//            "u.profileImgUrl AS imgSrc, " +
//            "u.email AS fileUrls," +
//            "b.boardName AS boardTypeStr) " +
//            "FROM Post p " +
//            "JOIN Board b ON p.boardType = b.boardType " +
//            "JOIN User u ON p.userId = u.userId " +
//            "WHERE p.postId = :postId")
//    PostDTO showDetailPost(@Param("postId") int postId);
//    //detailss
//
//    @Query("SELECT new com.dottree.nonogrammers.domain.CommentDTO(c.commentId AS commentId, c.postId AS postId, c.userId AS userId, c.content AS content, c.createdAt AS createdAt, c.updatedAt AS updatedAt, u.nickName AS nickName, u.profileImgUrl AS imgSrc) " +
//            "FROM Comment c " +
//            "JOIN User u ON c.userId = u.userId " +
//            "WHERE c.postId = :postId")
//    List<CommentDTO> commentList(@Param("postId") int postId);
////commList
//
////    @Query("SELECT count(*) as countC FROM comment c WHERE c.postId =:postId GROUP BY c.postId")
////    public List<Integer> NumberOfComments(String postId); //counting
//
////    @Query("insert into comment (postId,userId,content) values (:postId,:userId,:content)")
////    public boolean insertComment(CommentDTO cd); //insertComm
////
////    @Query("DELETE FROM comment WHERE commentId = :commentId")
////    public boolean deleteComment(CommentDTO cd); //deleteComm
//
//    @Query("DELETE FROM post WHERE postId = :postId")
//    public boolean deletePost(Post pd); //deletePost
    public void deleteByPostId(int postId);
//
////    @Query("UPDATE comment SET content = :content, updatedAt = now() WHERE commentId = :commentId")
////    public boolean editComment(CommentDTO cd); //editComm
//
////    @Query("update post set title = :title, content = :content, updatedAt = now(),boardType = :boardType where postId = :postId and userId = :userId")
////    public boolean editPost(Post pd); //editPost
    @Modifying
//    @Transactional
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.postId = :postId")
    public void incrementViewCount(@Param("postId") int postId);

    @Query("select b.boardType from Board b where b.boardName = :category")
    public int getBoardType(@Param("category") String category);

    // 게시글 작성
    @Transactional
    @Query("insert into Post (boardType, userId, title, content) values (:boardType, :userId, :title, :content)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Modifying
    public void insertPost(@Param("boardType") int boardType,@Param("userId") int userId,@Param("title") String title,@Param("content") String content);

    @Modifying
    @Transactional
    // 업로드된 이미지 정보 작성
    @Query("insert into File (postId, filename, fileExtension, fileUrl) values(:postId, :filename,:fileExtension, :fileUrl)")
    public void insertUploadImage(@Param("postId") int postId,@Param("filename") String filename,@Param("fileExtension") String fileExtension,@Param("fileUrl") String fileUrl);
}