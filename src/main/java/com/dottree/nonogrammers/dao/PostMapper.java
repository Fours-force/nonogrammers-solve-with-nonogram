package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
}
