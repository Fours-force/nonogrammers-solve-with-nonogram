package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.domain.UserDotDTO;
import com.dottree.nonogrammers.entity.UserSolvingRow;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserSolvingRowRepository extends JpaRepository<UserSolvingRow,Integer> {

    //user_solving_row에서 solvingRow 조회 / 지금 풀고 있는 행이 어디 인지 조회할 때 사용.
    @Query(value = "select u.solvingRow from UserSolvingRow u where u.user.userId = :#{#udDTO.userId} and u.nono.nonoId = :#{#udDTO.nonoId}")
    public Integer selectUserSolvingRow(@Param("udDTO")UserDotDTO udDTO);

    public UserSolvingRow findByUser_UserIdAndNono_NonoId(int userId, int nonoId);
}
