package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.UserDTO;
import com.dottree.nonogrammers.domain.JoinDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select userId, email, nickName, profileImgUrl from user where userId = #{userId}")
    public UserDTO selectUserByUserId(int userId);

    // 회원가입 - 유저 생성
    @Insert("insert into user (email, password, nickName, profileImgUrl) values (#{email}, #{password}, #{nickName}, #{profileImgUrl})")
    public boolean insertAccount(JoinDTO dto);

    // 이메일, 닉네임 중복 체크
    @Select("select count(*) from user where ${checkValue} = #{value}")
    public int getExists(@Param("checkValue") String checkValue,
                         @Param("value") String value
    );

    // 로그인 - 이메일, 비밀번호 확인
    @Select("select count(*) from user where email = #{email} and password = #{password}")
    public int getLogin(@Param("email") String email,
                        @Param("password") String password
    );

    // 비밀번호 찾기 - 이메일 확인
    @Select("select count(*) from user where email = #{email}")
    public int getEmail(@Param("email") String email);

    // 비밀번호 리셋
    @Update("update user set password = #{password} where email = #{email}")
    public boolean resetPassword(@Param("email") String email,
                                 @Param("password") String password
    );

    // 회원 탈퇴
    @Delete("delete from user where email = #{email}")
    public boolean deleteUser(@Param("email") String email);

    // 프로필 사진 수정
    @Update("update user set profileImgUrl = #{profileImgUrl} where email = #{email}")
    public boolean updateProfileImg(@Param("email") String email,
                                    @Param("profileImgUrl") String profileImgUrl
    );

}
