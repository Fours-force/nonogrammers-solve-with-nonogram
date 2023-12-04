package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserJoinService {

    private final UserRepository ur;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserJoinService(UserRepository ur, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.ur = ur;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public int join(JoinDTO joinDTO) throws Exception {
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .nickName(joinDTO.getNickName())
                .baekjoonUserId(joinDTO.getBaekjoonUserId())
                .roles("ROLE_USER")
                .build();
        return ur.save(user).getUserId();
    }
}
