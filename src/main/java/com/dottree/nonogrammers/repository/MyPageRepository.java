package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyPageRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);
}
