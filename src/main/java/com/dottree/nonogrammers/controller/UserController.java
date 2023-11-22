package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.domain.ResetPWDTO;
import com.dottree.nonogrammers.service.UserJoinService;
import com.dottree.nonogrammers.service.UserService;
import com.sun.net.httpserver.Headers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserJoinService userJoinService;
    private final UserService userService;

    public UserController(UserJoinService userJoinService, UserService userService) {
        this.userJoinService = userJoinService;
        this.userService = userService;
    }

    // role 테스트용 End-point
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public String user(Authentication authentication) {
        return "<h1>user</h1>";
    }

    @PostMapping(value = "/join", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity<Object> userJoin(@RequestBody JoinDTO joinDTO) {
        try {
            userJoinService.join(joinDTO);
            return ResponseEntity.ok("성공적으로 회원 가입이 진행되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/email-verification")
    public ResponseEntity<Object> joinEmailVerification(
            @RequestParam("email") String email,
            @RequestParam("type") String type) {
        try {

            // 이메일 중복 검사 (존재하면 true)
            boolean isEmail = userService.validateDuplicateEmail(email);

            // 이메일 전송 (인증번호 or 토큰)
            if (type.equals("join")) {
                if (isEmail) {
                    return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
                }
                userService.sendJoinEmail(email);
            } else if (type.equals("reset-password")) {
                if (!isEmail) {
                    return ResponseEntity.badRequest().body("존재하지 않는 이메일입니다.");
                }
                userService.sendResetPasswordEmail(email);
            } else {
                return ResponseEntity.badRequest().body("잘못된 요청입니다.");
            }
            return ResponseEntity.ok("성공적으로 이메일이 전송되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이메일 전송에 실패하였습니다.");
        }
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Object> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        boolean isNickname = userService.validateDuplicateNickname(nickname);
        return isNickname ? ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다.") : ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }

    @GetMapping("/check-baekjoon")
    public ResponseEntity<Object> verifyBaekjoonId(@RequestParam("id") String id) {
        // 중복 검증
        boolean isBeakjoonId = userService.validateBaekjoonId(id);
        boolean isNotDuplicateBeakjoonId = userService.validateDuplicateBJId(id);
        System.out.println("isBeakjoonId: " + isBeakjoonId);
        System.out.println("isNotDuplicateBeakjoonId: " + isNotDuplicateBeakjoonId);
        if (isBeakjoonId && isNotDuplicateBeakjoonId) {
            return ResponseEntity.ok("사용 가능한 백준 아이디입니다.");
        } else if (!isBeakjoonId) {
            return ResponseEntity.badRequest().body("존재하지 않는 백준 아이디입니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 존재하는 백준 아이디입니다.");
        }
    }

    @RequestMapping("/verify-code")
    public ResponseEntity<Object> verifyJoinCode(
            @RequestParam("email") String email,
            @RequestParam("code") String code) {
        try {
            String getCodeEmail = userService.validateEmailVerification(code);
            if (!getCodeEmail.equals(email)) {
                return ResponseEntity.badRequest().body("인증 코드가 일치하지 않습니다.");
            }
            return ResponseEntity.ok("인증번호가 일치합니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("문제가 발생하였습니다.");
        }
    }

//    @GetMapping("/verify-token")
//    public ResponseEntity<Object> verifyJoinToken(@RequestParam("email") String email, @RequestParam("token") String token) {
//        String getTokenEmail = userService.validateEmailVerification(token);
//        return ResponseEntity.ok("토큰 인증이 완료되었습니다.");
//    }

    @PutMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(
            @RequestParam("email") String email,
            @RequestHeader(value = "secretCode", required = true) String secretCode,
            ResetPWDTO resetPWDTO) {

        if (!secretCode.equals("nonog-ssssforce-nonogrammers-1")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("허용되지 않은 접근입니다.");
        }
        // '비밀번호', '비밀번호 확인' 두 값이 같은 지 확인
        if (!userService.validatePassword(resetPWDTO)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
        // 비밀번호 변경
        try {
            userService.setPassword(email, resetPWDTO.getPassword());
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}