package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.UserMapper;
import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.domain.ResponseModel;
import com.dottree.nonogrammers.service.UserJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserJoinService userJoinService;

    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    private final Map<String, String> emailTokenMap = new HashMap<>();
    @Autowired
    UserMapper dao;

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

    @RequestMapping(value = "/api/check/{checkValue}", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel isValue(
            @PathVariable("checkValue") String checkValue,
            @RequestParam("value") String value
    ) {

        ResponseModel response = new ResponseModel();
        response.setTitle(String.format("%s", checkValue));

        int result = dao.getExists(checkValue, value);
        if (result == 0) {
            response.setStatusCode(200);
            response.setMessage("OK");
        } else {
            response.setStatusCode(404);
            response.setMessage("It exists");
        }
        return response;
    }

    @RequestMapping(value = "/api/reset-password-token", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getEmailCheck(
            @RequestParam("email") String email
    ) {
        ResponseModel response = new ResponseModel();
        int result = dao.getEmail(email);

        if (result != 0) {
            response.setStatusCode(200);
            response.setMessage("이메일 전송");
            String token = UUID.randomUUID().toString();
            emailTokenMap.put(token, email);
            response.setData("api/check-reset-password?token=" + token);
        } else {
            response.setStatusCode(404);
            response.setMessage("등록된 이메일 주소가 아닙니다");
        }
        return response;
    }

    @GetMapping("/api/check-reset-password")
    public String showResetPassword(@RequestParam("token") String token, Model model) {
        String email = emailTokenMap.get(token);
        if (email != null) {
            emailTokenMap.remove(token);
            model.addAttribute("email", email);
            return "reset-password";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/api/reset-password", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel resetPassword(JoinDTO dto) {
        ResponseModel response = new ResponseModel();
        response.setTitle("reset-password");

        if (!dto.getPassword().equals(dto.getCorrectPassword())) {
            response.setStatusCode(400);
            response.setMessage("비밀번호가 맞지 않습니다.. 다시 시도해주세요🥲");
            return response;
        }

        boolean result = dao.updatePassword(dto);
        if (result) {
            response.setStatusCode(200);
            response.setMessage("비밀번호 변경이 완료되었습니다🎉");
        } else {
            response.setStatusCode(500);
            response.setMessage("! 문제가 발생했습니다. 다시 시도해주세요");
        }
        return response;
    }
}