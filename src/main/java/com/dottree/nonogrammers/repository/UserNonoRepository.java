package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.UserNono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNonoRepository extends JpaRepository<UserNono,Integer> {

    //사용자가 nonoId에 해당하는 노노를 풀고있는지 usernono에서 조회. 1이상이면 true 이면 false
    public Integer countByUser_UserIdAndNono_NonoId(int userId, int nonoId);

    public Optional<UserNono> findByUser_UserIdAndNono_NonoId(int userId, int nonoId);



}
