package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.dao.CommentMapper;
import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.File;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.repository.CommentRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;
    public CommentService(CommentRepository CommentRepository, CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Integer> CountByPostId(int postId){
        List<Integer> counts = commentRepository.countByPost_PostId(postId);
        return counts;
    }
//    @Transactional
//    public void InsertComment(int postId,int userId, String content){
//        commentRepository.insertComment(postId,userId,content);
//    }
    @Transactional
    public Comment InsertComment(CommentDTO commentModel, Post post) {
        Comment comment = commentModel.toEntity(post);
        return commentRepository.save(comment);
    }
    @Transactional
    public void DeleteComment(int commentId){
        commentRepository.deleteByCommentId(commentId);
    }
    @Transactional
    public void EditComment(String content,int commentId){
        commentRepository.editComment(content,commentId);
    }


    public List<CommentDTO> CommentList(int postId){
        List<Comment> list = commentRepository.findByPost_PostIdOrderByCreatedAt(postId);
        return list.stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }
}
