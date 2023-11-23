package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.config.RedisUtil;
import com.dottree.nonogrammers.domain.ResetPWDTO;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository ur;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisUtil redisUtil;
    private final EmailService emailService;

    // 이메일 전송 (5분 제한) -> 회원가입, 비밀번호 재설정
    public void sendJoinEmail(String email) {
        String randomNumber = generateRandomNumber();
        redisUtil.setData(randomNumber, email, 300000);
        emailService.sendNumberEmail(email, randomNumber);
    }

    public void sendResetPasswordEmail(String email) throws MessagingException {
        String token = generateToken();
        redisUtil.setData(token, email, 300000);
        emailService.sendLinkEmail(email, token);
    }

    public String generateRandomNumber() {
        return String.valueOf((int) (Math.random() * 10000));
    } // 난수 생성

    public String generateToken() {
        return UUID.randomUUID().toString();
    }  // 토큰 생성

    // 토큰 및 난수 인증 (Redis)
    public String validateEmailVerification(String key) {
        String email = redisUtil.getData(key);
        if (email == null) { // 잘못된 인증번호(or 토큰)이거나 인증 시간 만료
            throw new IllegalStateException("인증 시간 만료 또는 잘못된 입력입니다.");
        }
        return email;
    }

    // 이메일 중복 체크
    public boolean validateDuplicateEmail(String email) {
        Optional<User> user = ur.findByEmail(email);
        return user.isPresent();
    }

    // 닉네임 중복 체크
    public boolean validateDuplicateNickname(String nickname) {
        Optional<User> user = ur.findByNickName(nickname);
        return user.isPresent();
    }

    // 백준 아이디 검증
    public boolean validateBaekjoonId(String baekjoonId) {

        StringBuilder url = new StringBuilder();

        url.append("https://www.acmicpc.net/user/");
        url.append(baekjoonId);
        try {
            Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    // 백준 아이디 중복 체크
    public boolean validateDuplicateBJId(String baekjoonId) {
        Optional<User> user = ur.findByBaekjoonUserId(baekjoonId);
        return user.isEmpty();
    }

    // 비밀번호 == 비밀번호 확인
    public boolean validatePassword(ResetPWDTO resetPWDTO) {
        return resetPWDTO.getPassword().equals(resetPWDTO.getCorrectPassword());
    }

    // 비밀번호 변경
    @Transactional(rollbackFor = SQLException.class)
    public void setPassword(String email, String password) {
        User user = ur.findByEmail(email).orElseThrow(() -> new IllegalStateException("가입되지 않은 이메일입니다."));
        user.changePassword(bCryptPasswordEncoder.encode(password));
    }

}
