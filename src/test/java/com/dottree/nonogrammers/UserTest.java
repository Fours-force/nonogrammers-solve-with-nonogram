package com.dottree.nonogrammers;

import com.dottree.nonogrammers.domain.User;
import com.dottree.nonogrammers.repository.UserRepository;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityListeners(AuditingEntityListener.class)
public class UserTest {

    @Autowired
    private UserRepository ur;

    @Test
    public void test() {
        ur.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional
    public void test2() {
        User user = User.builder()
                .email("email@email")
                .password("password")
                .nickName("Isnickname")
                .baekjoonUserId("IsbeakjoonuserId").build();
        ur.save(user);
        ur.findAll().forEach(System.out::println);
    }

}
