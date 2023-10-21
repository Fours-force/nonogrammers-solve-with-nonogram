package com.dottree.nonogrammers.dao;

import com.dottree.nonogrammers.domain.*;
import org.apache.ibatis.annotations.*;
import groovy.util.logging.Slf4j;

import java.util.List;

@Mapper
public interface MainMapper {

    //사용자가 nonoId에 해당하는 노노를 풀고있는지 usernono에서 조회. 1이상이면 true 이면 false
    @Select("select count(userId) from usernono where userId = #{userId} and nonoId = #{nonoId}")
    public int selectUserFromUserNono(UserNonoDTO unDTO);

    //노노 개방. usernono에 누가 어떤 노노 풀고있는지 삽입. // 중복 삽입되는 문제 발생.
    @Insert("insert into usernono (userId, nonoId, isSolved) values (#{userId}, #{nonoId}, #{solvedStat})")
    public void insertUserNono(UserNonoDTO unDTO, @Param("solvedStat")int solvedStat);

    //usernono에 isSolved 갱신 / 노노 개방시, 문제 다 풀었을 때 상태 갱신.
    @Update("update usernono set isSolved = #{solvedStat} where userId = #{userId} and nonoId = #{nonoId}")
    public void updateUserNonoIsSolved(@Param("solvedStat")int solvedStat, UserNonoDTO unDTO);

    //dotView()에서 dot에 엑셀파일의 도트정보 삽입
    @Insert("insert into dot (nonoId, color) values (#{nonoId}, #{color})")
    public void insertDotsInDot(DotDTO dDTO);

    //dot에서 모든 도트정보 조회
    @Select("select * from dot where nonoId = #{nonoId}")
    public List<DotDTO> selectAllDot(@Param("nonoId") int nonoId);

    //nono에서 행마다 Url 뒤에 넣어줄 문제의 번호 전체 조회
    @Select("select allProblemToStr from nono where nonoId = #{nonoId}")
    public NonoDTO selectAllallProblemToStr(@Param("nonoId") int nonoId);

    //userdot에서 해결한 도트들의 수 조회 / Progress Bar에 사용.
    @Select("select count(userDotId) from userdot where userId = #{userId} and dotId = #{dotId}")
    public int selectSolvedNumber(UserDotDTO udDTO); // div태그에 대해서 count를 해야함. 지금은 모든 도트의 개수임. count를 32로 나누면 될듯?

    //userdot에서 dotId하나 조회 / dotId 중복 확인을 위해서 사용.
    @Select("select dotId from userdot where userId = #{userId} and nonoId = #{nonoId} and dotId = #{dotId}")
    public UserDotDTO selectIsDotsSolved(UserDotDTO udDTO);

    //userdot에서 해결한 dotId 전체를 조회 / 색칠 해주기 위해서 사용.
    @Select("select dotId from userdot where userId = #{userId} and nonoId = #{nonoId}")
    public List<UserDotDTO> selectSolvedDotId(UserDotDTO udDTO);

    //문제 해결시 userdot에 해당 행의 도트들 삽입
    @Insert("insert into userdot (nonoId, dotId, userId) values (#{nonoId},#{dotId},#{userId})")
    public void insertUserDot(UserDotDTO udDTO);

    //userdot에서 이미 푼 행의 도트인지 확인
    @Select("select count(dotId) from userdot where userId = #{userId} and nonoId = #{nonoId} and dotId = #{dotId}")
    public int selectUserDotIsSolved(UserDotDTO udDTO);

    //user_solving_row에서 solvingRow 조회 / 지금 풀고 있는 행이 어디 인지 조회할 때 사용.
    @Select("select solvingRow from user_solving_row where userId = #{userId} and nonoId = #{nonoId}")
    public int selectUserSolvingRow(UserDotDTO udDTO);

    //user_solving_row에 solvingRow 삽입. / 문제를 클릭 했을 때 지금 풀고 있는 행 저장.
    @Insert("insert into user_solving_row (userId, nonoId, solvingRow) values (#{userId}, #{nonoId}, #{solvingRow})")
    public void insertUserSolvingRow(UserSolvingRowDTO usrDTO);

    //user_solving_row에 slovingRow 갱신. / 현재 풀고있는 문제의 행 정보 갱신.
    @Update("update user_solving_row set solvingRow = #{solvingRow} where userId = #{userId} and nonoId = #{nonoId}")
    public void updateUserSolvingRow(UserSolvingRowDTO usrDTO);
    // user_solving_row의 solvingRow 0으로 초기화 / 문제 풀면 풀고있는 문제의 행 0으로 바꿈
    @Update("update user_solving_row set solvingRow=0 where userId = #{userId} and nonoId =#{nonoId}")
    public void resetUserSolvingRow(UserDotDTO udDTO);

    // user_solved_count에 userId,solvedCount 삽입 / 노노 개방 시 같이 넣어줌 -> 회원가입 시 넣어야될 듯.
    @Insert("insert into user_solved_count (userId, solvedCount) values (#{userId}, 0)")
    public void insertUserSolvedCount(UserNonoDTO unDTO);

    // user_solved_count에 userId와 solvedCount 갱신 / 문제 풀었을 때 문 문제수 +1
    @Update("update user_solved_count set solvedCount = #{solvedCount} where userId = #{userId}")
    public void updateUserSolvedCount(UserSolvedCountDTO uscDTO);

    // user_solved_count에서 solvedCount조회
    @Select("select solvedCount from user_solved_count where userId = #{userId}")
    public int selectUserSolvedCount(UserDotDTO udDTO);

}
