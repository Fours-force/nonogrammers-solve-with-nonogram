package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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
  
    @Select("SELECT postId, boardType, userId, title, content, createdAt, updatedAt  FROM post WHERE userId = #{userId}")
    public List<PostDTO> selectPostList(int userId);

    // boardName 으로 boardType 반환
    @Select("select boardType from board where boardName = #{category}")
    public int getBoardType(String category);

    // 게시글 작성
    @Insert("insert into post(boardType, userId, title, content) values (#{boardType}, #{userId}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public boolean insertPost(PostDTO dto);

    // 업로드된 이미지 정보 작성
    @Insert("insert into file(postId, filename, fileExtension, fileUrl) values(#{postId}, #{filename}, #{fileExtension}, #{fileUrl})")
    public boolean insertUploadImage(FileDTO dto);

}
