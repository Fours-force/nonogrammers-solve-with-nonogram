package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.DotDTO;
import com.dottree.nonogrammers.domain.UserNonoDTO;
import groovy.util.logging.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MainMapper {

    //사용자가 nonoId에 해당하는 노노를 풀고있는지 체크. 1이상이면 true 이면 false
    @Select("select count(userId) from usernono where userId = #{userId} and nonoId = #{nonoId}")
    public int selectUserFromUserNono(@Param("userId") int userId,@Param("noonId") int nonoId);

    //노노 개방. usernono테이블에 누가 어떤 노노 풀고있는지 저장.
    @Insert("insert into usernono (userId, nonoId) values (#{userId}, #{nonoId})")
    public void insertUserNono(@Param("userId") int userId, @Param("noonId") int nonoId);

    @Insert("insert into dot (nonoId, color) values (#{nonoId}, #{color})")
    public void insertDotsInDot(DotDTO dDTO);

//    @Select("select ")



}
