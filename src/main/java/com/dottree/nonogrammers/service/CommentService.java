package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.repository.CommentRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public CommentService(CommentRepository CommentRepository, CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Integer> CountByPostId(int postId){
        List<Integer> counts = commentRepository.countByPostId(postId);
        return counts;
    }
    @Transactional
    public void InsertComment(int postId,int userId, String content){
        commentRepository.insertComment(postId,userId,content);
    }
    @Transactional
    public void DeleteComment(int commentId){
        commentRepository.deleteComment(commentId);
    }
    @Transactional
    public void EditComment(String content,int commentId){
        commentRepository.editComment(content,commentId);
    }
}
