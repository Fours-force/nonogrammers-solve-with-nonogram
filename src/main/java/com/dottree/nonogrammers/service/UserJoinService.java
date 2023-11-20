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
        validateDuplicateUser(joinDTO);
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .nickName(joinDTO.getNickName())
                .baekjoonUserId(joinDTO.getBaekjoonUserId())
                .roles("ROLE_USER")
                .build();
        System.out.println(user);
        return ur.save(user).getUserId();
    }

    private void validateDuplicateUser(JoinDTO joinDTO) {
        ur.findByEmail(joinDTO.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
