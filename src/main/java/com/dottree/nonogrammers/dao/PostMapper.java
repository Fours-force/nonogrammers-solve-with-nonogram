package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.PostDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostMapper {
    @Select("select boardType,title,content,userId,createdAt from post")
    public List<PostDTO> listm();

}
