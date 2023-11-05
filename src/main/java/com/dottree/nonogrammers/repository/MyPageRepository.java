package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPageRepository extends JpaRepository<UserDTO, Integer> {

}
