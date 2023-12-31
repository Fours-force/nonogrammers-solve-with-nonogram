package com.dottree.nonogrammers.repository;


import com.dottree.nonogrammers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickname);

    Optional<User> findByBaekjoonUserId(String baekjoonId);
}
