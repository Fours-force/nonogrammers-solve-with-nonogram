package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.Dot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DotRepository extends JpaRepository<Dot,Integer> {

    //dot에서 모든 도트정보 조회
    public List<Dot> findAllByNono_NonoId(int nonoId);

    //dot에서 모든 dot의 수 조회 selectAllDotCount
    public Long countByNono_NonoId(int nonoId);


}
