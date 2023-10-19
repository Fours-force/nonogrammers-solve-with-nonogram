package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.UserMapper;
import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.domain.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class UserController {
    private final Map<String, String> emailTokenMap = new HashMap<>();
    @Autowired
    UserMapper dao;

    @RequestMapping(value = "/join", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel createAccount(
            JoinDTO dto,
            Model model
    ){
        ResponseModel response = new ResponseModel();
        response.setTitle("join");
        if (!dto.getPassword().equals(dto.getCorrectPassword())){
            response.setStatusCode(400);
            response.setMessage("비밀번호가 맞지 않습니다.. 다시 시도해주세요🥲");
            return response;
        }
        dto.setProfileImgUrl("/images/kang.png");
        try{
            boolean result = dao.insertAccount(dto);
            if (result){
                response.setStatusCode(201);
                response.setMessage("환영합니다! 회원가입이 완료되었습니다");
            } else {
                response.setStatusCode(500);
                response.setMessage("문제가 생겼어요! 다시 시도해주세요");
            }
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("! 중복된 항목이 존재합니다");
            return response;
        }
        return response;
    }

    @RequestMapping(value = "/check/{checkValue}", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel isValue(
            @PathVariable("checkValue") String checkValue,
            @RequestParam("value") String value
    ){
        ResponseModel response = new ResponseModel();
        response.setTitle(String.format("%s", checkValue));

        int result = dao.getExists(checkValue, value);
        if (result==0) {
            response.setStatusCode(200);
            response.setMessage("OK");
        } else{
            response.setStatusCode(404);
            response.setMessage("It exists");
        }
        return response;
    }

    @RequestMapping(value = "/reset-password-token", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getEmailCheck(
            @RequestParam("email") String email
    ){
        ResponseModel response = new ResponseModel();
        int result = dao.getEmail(email);

        if (result!=0) {
            response.setStatusCode(200);
            response.setMessage("이메일 전송");
            String token = UUID.randomUUID().toString();
            emailTokenMap.put(token, email);
            response.setData("api/check-reset-password?token="+token);
        } else{
            response.setStatusCode(404);
            response.setMessage("등록된 이메일 주소가 아닙니다");
        }
        return response;
    }

    @GetMapping("/check-reset-password")
    public ModelAndView showResetPassword(@RequestParam("token") String token){
        ModelAndView mav = new ModelAndView();
        String email = emailTokenMap.get(token);
        if (email != null){
            emailTokenMap.remove(token);
            mav.addObject("email", email);
            mav.setViewName("reset-password");
        } else {
            mav.setViewName("home");
        }
        return mav;
    }

    @RequestMapping(value = "/reset-password", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel resetPassword(JoinDTO dto){
        ResponseModel response = new ResponseModel();
        response.setTitle("reset-password");

        if (!dto.getPassword().equals(dto.getCorrectPassword())){
            response.setStatusCode(400);
            response.setMessage("비밀번호가 맞지 않습니다.. 다시 시도해주세요🥲");
            return response;
        }

        boolean result = dao.updatePassword(dto);
        if (result){
            response.setStatusCode(200);
            response.setMessage("비밀번호 변경이 완료되었습니다🎉");
        } else {
            response.setStatusCode(500);
            response.setMessage("! 문제가 발생했습니다. 다시 시도해주세요");
        }
        return response;
    }
}