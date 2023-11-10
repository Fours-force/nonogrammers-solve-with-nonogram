package com.dottree.nonogrammers;

import com.dottree.nonogrammers.repository.CommentRepository;
import com.dottree.nonogrammers.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostTest {
    @Autowired
    private PostRepository pr;

    @Autowired
    private CommentRepository cr;

    @Test
    public void test() {
        pr.findAll().forEach(System.out::println);
    }

}

