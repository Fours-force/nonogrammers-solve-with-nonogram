package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.UserSolvedCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSolvedCountRepository extends JpaRepository<UserSolvedCount, Integer> {

}
