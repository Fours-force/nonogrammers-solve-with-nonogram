package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.UserDTO;
import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.domain.UserNonoDTO;
import com.dottree.nonogrammers.domain.UserNonoVO;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 계정관리 컨트롤러에 응답해줄 user 정보
     * @param userId
     * @return UserDTO
     */
    @Select("select userId, email, nickName, profileImgUrl, changedAt, baekjoonUserId from user where userId = #{userId}")
    public UserDTO selectUserByUserId(int userId);

    // 회원가입 - 유저 생성
    @Insert("insert into user (email, password, nickName, profileImgUrl, baekjoonUserId) values (#{email}, #{password}, #{nickName}, #{profileImgUrl}, #{baekjoonUserId})")
    public boolean insertAccount(JoinDTO dto);

    // 이메일, 닉네임 중복 체크
    @Select("select count(*) from user where ${checkValue} = #{value}")
    public int getExists(@Param("checkValue") String checkValue,
                         @Param("value") String value
    );

    // 로그인 - 이메일, 비밀번호 확인
    @Select("select userId from user where email = #{email} and password = #{password} and statusCode=1")
    public Integer getLogin(@Param("email") String email,
                        @Param("password") String password
    );

    // 비밀번호 찾기 - 이메일 확인
    @Select("select count(*) from user where email = #{email}")
    public int getEmail(@Param("email") String email);

    // 비밀번호 리셋
    @Update("update user set password = #{password}, changedAt= now()  where email = #{email}")
    public boolean updatePassword(JoinDTO dto);

    // 회원 탈퇴
    @Delete("delete from user where email = #{email}")
    public boolean deleteUser(@Param("email") String email);

    // 프로필 사진 수정
    @Update("update user set profileImgUrl = #{profileImgUrl} where email = #{email}")
    public boolean updateProfileImg(@Param("email") String email,
                                    @Param("profileImgUrl") String profileImgUrl
    );

    /**
     * 유저 닉네임 수정
     * @param email
     * @param nickName
     * @return boolean
     */
    @Update("update user set nickName = #{nickName} where email = #{email}")
    public boolean resetNickname(@Param("email") String email,
                             @Param("nickName") String nickName);

    /**
     * 회원 탈퇴 시 statusCode 컬럼 0으로 변경
     * @param email
     * @param statusCode
     * @return boolean
     */
    @Update("update user set statusCode = #{statusCode} where email = #{email}")
    public boolean updateStatusCode(@Param("email") String email,
                                    @Param("statusCode") int statusCode);


    /**
     * 유저가 푼 노노들을 보여주기
     * @param userId
     * @param isSolved
     * @return List<UserNonoVO>
     */
    @Select("SELECT n.nonoId, u.userId, n.nonoImgUrl, u.isSolved, u.createdAt from usernono u Inner JOIN nono n on u.nonoId = n.nonoId WHERE u.userId= #{userId} and u.isSolved = #{isSolved}")
    @Results({
//            @Result(property = "userNonoId", column = "userNonoId"),
            @Result(property = "nonoId", column = "nonoId"),
            @Result(property = "userId", column = "userId"),
            @Result(property = "nonoImgUrl", column = "nonoImgUrl"),
            @Result(property = "isSolved", column = "isSolved"),
            @Result(property = "createdAt", column = "createdAt")
    })
    public List<UserNonoVO> selectUserNonoByIsSolved(@Param("userId") int userId, @Param("isSolved") int isSolved);


    /**
     * 유저가 풀고있는 노노(문제집) 조회
     * @param userId
     * @return UserNonoDTO
     */
    @Select("select userNonoId, userId, nonoId, createdAt, isSolved from usernono where userId = #{userId} and nonoId = #{nonoId} and isSolved = 0")
    public UserNonoVO selectIngUserNonoDetail(@Param("userId") int userId, @Param("nonoId") int nonoId);

    /**
     * 유저가 푼 노노들 보여주기
     * @param userId
     * @param nonoId
     * @return
     */
    @Select("select userNonoId, userId, nonoId, createdAt, isSolved from usernono where userId = #{userId} and nonoId = #{nonoId} and isSolved = 1")
    public UserNonoVO selectSolvedUserNonoDetail(@Param("userId") int userId, @Param("nonoId") int nonoId);
}
