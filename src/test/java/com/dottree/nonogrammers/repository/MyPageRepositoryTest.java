package com.dottree.nonogrammers.repository;

import com.dottree.nonogrammers.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class MyPageRepositoryTest {
//    @MockBean
    private final MyPageRepository repo;

    @Autowired
    public MyPageRepositoryTest(MyPageRepository repo) {
        this.repo = repo;
    }

    @BeforeEach
    @Transactional
    public void 유저생성() {
        LocalDate dt = LocalDate.now();
        User user = User.builder()
                    .email("ee@ee")
                    .nickName("test111")
                    .baekjoonUserId("jasob")
                    .password("pass")
                    .profileImgUrl("/images/profile")
                    .statusCode(1)
                    .build();
        repo.save(user);
    }

    @Test
    @Transactional
    @Order(1)
    public void 닉네임변경테스트() {
        Optional<User> userOpt = repo.findById(1);
        User user = userOpt.get();
        String oldNickname = user.getNickName();

        user = user.toBuilder()
                .nickName("testNickName")
                .build();

        repo.save(user);
        String newNickname = user.getNickName();

        assertNotEquals(oldNickname, newNickname);
    }

    @Test
    @Transactional
    @Order(2)
    public void 회원탈퇴테스트() {
        Optional<User> userOpt = repo.findById(1);
        User user = userOpt.get();

        int oldStatusCode = user.getStatusCode();
        user = user.toBuilder()
                .statusCode(0)
                .build();
        int newStatusCode = user.getStatusCode();

        assertNotEquals(oldStatusCode, newStatusCode);
    }
}