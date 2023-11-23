package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.domain.UserDTO;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.repository.PostRepository;
import com.dottree.nonogrammers.repository.UserGetRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserGetService {
    private final UserGetRepository userGetRepository;
    //    private final CommentRepository commentRepository;
//    private final PostMapper postMapper = PostMapper.INSTANCE;
//    @Autowired
//    private EntityManager entityManager;

    public UserGetService(UserGetRepository userGetRepository) {
        this.userGetRepository = userGetRepository;
    }
    public UserDTO FindByNickName(String nickName){
        User user = userGetRepository.findByNickName(nickName);
        return user.toDto();
    }
    public UserDTO FindByUserId(String userId){
        User user = userGetRepository.findByUserId(userId);
        return user.toDto();
    }
}
