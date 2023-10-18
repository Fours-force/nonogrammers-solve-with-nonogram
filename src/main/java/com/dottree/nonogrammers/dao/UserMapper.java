package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select userId, email, nickName, profileImgUrl from user where userId = #{userId}")
    public UserDTO selectUserByUserId(int userId);
}
