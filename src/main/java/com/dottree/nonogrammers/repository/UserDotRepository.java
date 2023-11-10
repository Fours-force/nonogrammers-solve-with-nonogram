package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.domain.UserDotDTO;
import com.dottree.nonogrammers.entity.UserDot;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDotRepository extends JpaRepository<UserDot,Integer> {

    //userdot에서 해결한 도트들의 수 조회 / Progress Bar에 사용.
    public Long countUserDotByUserIdAndNonoId(int userId, int nonoId);

/*    //userdot에서 dotId하나 조회 / dotId 중복 확인을 위해서 사용. 후에 -1 처리 해야함.
    @Query("SELECT u.dot.dotId FROM UserDot u WHERE u.user.userId = :#{#udDTO.userId} AND u.nono.nonoId = :#{#udDTO.nonoId} AND u.dot.dotId = :#{#udDTO.dotId}")
    public Integer selectIsDotsSolved(@Param("udDTO")UserDotDTO udDTO);

    //userdot에서 해결한 dotId 전체를 조회 / 색칠 해주기 위해서 사용. 후에 -1 처리 해야함.
    @Query("select u.dot.dotId from UserDot u where u.user.userId = :userId and u.nono.nonoId = :nonoId")
    public List<Long> selectSolvedDotId(@Param("userId") int userId, @Param("nonoId") int nonoId);*/

    //userdot에서 dotId하나 조회 / dotId 중복 확인을 위해서 사용. 후에 -1 처리 해야함.
    @Query("SELECT u.dotId FROM UserDot u WHERE u.userId = :#{#udDTO.userId} AND u.nonoId = :#{#udDTO.nonoId} AND u.dotId = :#{#udDTO.dotId}")
    public Integer selectIsDotsSolved(@Param("udDTO")UserDotDTO udDTO);

    //userdot에서 해결한 dotId 전체를 조회 / 색칠 해주기 위해서 사용. 후에 -1 처리 해야함.
    @Query("select u.dotId from UserDot u where u.userId = :userId and u.nonoId = :nonoId")
    public List<Long> selectSolvedDotId(@Param("userId") int userId, @Param("nonoId") int nonoId);

    //userdot에서 이미 푼 행의 도트인지 확인
    public Long countByUserIdAndNonoIdAndDotId(int userId, int nonoId, int dotId);

}