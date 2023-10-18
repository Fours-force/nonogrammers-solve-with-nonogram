package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.UserMapper;
import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.domain.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {

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

}