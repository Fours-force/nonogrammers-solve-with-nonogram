package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}