package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.File;
import com.dottree.nonogrammers.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Integer> {
    public List<File> findByPost_PostId(int postId);
}
