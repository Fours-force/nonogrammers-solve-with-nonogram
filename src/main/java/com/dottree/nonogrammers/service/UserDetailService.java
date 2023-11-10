package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.User;
import com.dottree.nonogrammers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository ur;

    @Autowired
    public UserDetailService(UserRepository ur) {
        this.ur = ur;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        return ur.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일입니다. : " + email));
    }
}