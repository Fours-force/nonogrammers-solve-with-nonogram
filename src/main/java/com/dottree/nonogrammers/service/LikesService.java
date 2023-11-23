package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.dao.CommentMapper;
import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.LikeDTO;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Likes;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.repository.CommentRepository;
import com.dottree.nonogrammers.repository.LikesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesService {
    private final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }
    public int CountByPostId(int postId){
        int counts=likesRepository.countByPost_PostId(postId);
        return counts;
    }
    public int CountByUserIdAndPostId(int userId, int postId){
        int counts = likesRepository.countByUser_UserIdAndPost_PostId(userId,postId);
        return counts;
    }
    public void DeleteByPostId(int postId){
        likesRepository.deleteByPost_PostId(postId);
    }
    @Transactional
    public Likes addPostLike(LikeDTO LikesModel, User user, Post post) {
        Likes likes = LikesModel.toEntity(user,post);
        return likesRepository.save(likes);
    }
}
