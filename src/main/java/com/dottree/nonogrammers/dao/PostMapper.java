package com.dottree.nonogrammers.dao;
import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.domain.LikeDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Likes;
import com.dottree.nonogrammers.entity.Post;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

//    @Mapping(target = "fileUrls", source = "")
    @Mapping(target = "commentCount", expression  = "java(getCommentCount(post))")
    @Mapping(target = "likeCount", expression  = "java(getLikeCount(post))")
    @Mapping(target = "nickName", source = "post.userNickName.nickName")  // 다른 테이블에 있는 속성 매핑
    @Mapping(target = "imgSrc", source = "post.userImgSrc.profileImgUrl")  // 다른 테이블에 있는 속성 매핑
    @Mapping(target = "boardTypeStr", source = "post.board.boardName")
    PostDTO postToPostDTO(Post post);
    default long getCommentCount(Post post) {
        return post.getComment().stream()
                .filter(comment -> comment.getPost().getPostId() == post.getPostId())
                .count();
    }
    default long getLikeCount(Post post) {
        return post.getLikes().stream()
                .filter(likes -> likes.getPost().getPostId() == post.getPostId())
                .count();
    }
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p INNER JOIN user AS u ON p.userId = u.userId ORDER BY p.createdAt DESC")
//    public List<PostDTO> listm();
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p JOIN user AS u ON p.userId = u.userId where p.title like concat('%',#{keyword},'%') ORDER BY p.createdAt DESC")
//    public List<PostDTO> search(String keyword);
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=1 ORDER BY p.createdAt DESC")
//    public List<PostDTO> listN();
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=2 ORDER BY p.createdAt DESC")
//    public List<PostDTO> listF();
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=3 ORDER BY p.createdAt DESC")
//    public List<PostDTO> listQ();
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p LEFT JOIN user AS u ON p.userId = u.userId where p.boardType=4 ORDER BY p.createdAt DESC")
//    public List<PostDTO> listNN();
//    @Select("SELECT p.postId, p.boardType, p.title, p.content, p.userId, p.createdAt, p.updatedAt, (SELECT COUNT(*) FROM comment AS c WHERE c.postId = p.postId) AS commentCount,(SELECT COUNT(*) FROM likes AS l WHERE l.postId = p.postId) AS likeCount, p.viewCount, b.boardName as boardTypeStr, u.nickName as nickName, u.profileImgUrl as imgSrc,(SELECT GROUP_CONCAT(fileUrl) FROM file AS f WHERE f.postId = p.postId) AS fileUrls FROM post AS p JOIN board AS b ON p.boardType = b.boardType JOIN user AS u ON p.userId = u.userId WHERE p.postId = #{postId}")
//    public PostDTO detailss(String postId);
//    @Select("SELECT c.commentId, c.postId, c.userId, c.content, c.createdAt, c.updatedAt, u.nickName as nickName, u.profileImgUrl as imgSrc FROM comment AS c JOIN user AS u ON c.userId = u.userId WHERE c.postId = #{postId}")
//    public List<CommentDTO> commList(String postId);
//    @Select("SELECT count(*) as countC FROM comment WHERE postId =#{postId} GROUP BY postId")
//    public List<Integer> counting(String postId);
//    @Insert("insert into comment (postId,userId,content) values (#{postId},#{userId},#{content})")
//    public boolean insertComm(CommentDTO cd);
//    @Delete("DELETE FROM comment WHERE commentId = #{commentId}")
//    public boolean deleteComm(CommentDTO cd);
//    @Delete("DELETE FROM post WHERE postId = #{postId}")
//    public boolean deletePost(PostDTO pd);
//    @Update("UPDATE comment SET content = #{content}, updatedAt = now() WHERE commentId = #{commentId}")
//    public boolean editComm(CommentDTO cd);
//    @Update("update post set title = #{title}, content = #{content}, updatedAt = now(),boardType = #{boardType} where postId = #{postId} and userId = #{userId}")
//    public boolean editPost(PostDTO pd);
//
//    /**
//     * 유저의 게시글 목록 보여주기
//     * @param userId
//     * @return List<PostDTO>
//     */
//    @Select("SELECT postId, boardType, userId, title, content, createdAt, updatedAt  FROM post WHERE userId = #{userId}")
//    public List<PostDTO> selectPostList(int userId);
//
//    /**
//     * 유저의 게시글 수정하기
//     * @param title
//     * @param content
//     * @param postId
//     * @param userId
//     * @return boolean
//     */
//    @Update("update post set title = #{title}, content = #{content}, updatedAt = now() where postId = #{postId} and userId = #{userId}")
//    public boolean updatePostByPostIdAndUserId(@Param("title") String title, @Param("content") String content, @Param("postId") int postId, @Param("userId") int userId);
//
//
//    @Update("UPDATE post SET viewCount = viewCount + 1 WHERE postId = #{postId}")
//    public void incrementViewCount(String postId);
//
//
//    // boardName 으로 boardType 반환
////    @Select("select boardType from board where boardName = #{category}")
////    public int getBoardType(String category);
////
////    // 게시글 작성
////    @Insert("insert into post(boardType, userId, title, content) values (#{boardType}, #{userId}, #{title}, #{content})")
////    @Options(useGeneratedKeys = true, keyProperty = "id")
////    public boolean insertPost(PostDTO dto);
////
////    // 업로드된 이미지 정보 작성
////    @Insert("insert into file(postId, filename, fileExtension, fileUrl) values(#{postId}, #{filename}, #{fileExtension}, #{fileUrl})")
////    public boolean insertUploadImage(FileDTO dto);
//    @Update("update file SET filename= #{filename}, fileExtension = #{fileExtension},fileUrl = #{fileUrl} WHERE postId = #{postId}")
//    public boolean updateUploadImage(FileDTO dto);
//
//    // 해당 게시글의 좋아요 개수
//    @Select("select count(*) from likes where postId=#{postId}")
//    public int getPostLike(int postId);
//
//    // 게시글 좋아요 여부 조회
//    @Select("select count(*) from likes where userId=#{userId} and postId=#{postId}")
//    public int getUserPostLike(LikeDTO dto);
//
//    // 게시글 좋아요 정보 추가
//    @Insert("insert into likes(userId, postId) values(#{userId}, #{postId})")
//    public boolean addPostLike(LikeDTO dto);
//
//    // 게시글 좋아요 정보 삭제
//    @Delete("delete from likes where userId=#{userId} and postId=#{postId}")
//    public boolean delPostLike(LikeDTO dto);
}
