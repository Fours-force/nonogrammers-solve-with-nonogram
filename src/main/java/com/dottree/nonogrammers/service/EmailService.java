package com.dottree.nonogrammers.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNumberEmail(String to, String number) {
        String text = "안녕하세요!\n" +
                "노노그래머스입니다.\n " +
                "인증 번호는 " + number + "입니다.\n" +
                "감사합니다.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[노노그래머스] 회원가입 이메일 인증 메일입니다.");
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendLinkEmail(String to, String token) throws MessagingException {
        // frontend url로 변경 필요
        // localhost:5173/reset-password, http://localhost:8089/api/v1/auth/verify-code?email=%s&code=%s
        String verificationUrl = String.format("http://localhost:5173/reset-password?email=%s&code=%s", to, token);
        String text = "<h1> 안녕하세요! 노노그래머스입니다. </h1>" +
                "<br><p> 아래 링크를 클릭하면 이메일 인증이 완료됩니다. </p>" +
                "<br><a href='" + verificationUrl + "'>이메일 인증하기</a><br>" +
                "<br><p>감사합니다.</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("[노노그래머스] 회원가입 이메일 인증 메일입니다.");
        message.setText(text, "utf-8", "html");
        javaMailSender.send(message);
    }
}

