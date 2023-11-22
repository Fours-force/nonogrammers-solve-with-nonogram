package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGetRepository extends JpaRepository<User,Integer> {
    public User findByNickName(String nickName);
}
