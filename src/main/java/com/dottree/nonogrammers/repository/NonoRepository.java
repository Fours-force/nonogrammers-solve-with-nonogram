package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.Nono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NonoRepository extends JpaRepository<Nono, Integer> {
    /**
     * nono에서 행마다 Url 뒤에 넣어줄 문제의 번호 전체 조회
     * @param nonoId
     * @return
     */
    public Nono findByNonoId(int nonoId);
    /**
     * nono에서 레벨 별 노노 가져오기
     * @param levelType
     * @return
     */
    public List<Nono> findByLevelType(int levelType);



}
