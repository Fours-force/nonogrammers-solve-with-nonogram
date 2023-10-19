package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.UserMapper;
import com.dottree.nonogrammers.domain.JoinDTO;
import com.dottree.nonogrammers.domain.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api")
public class JoinController {

    @Autowired
    UserMapper dao;

    @PostMapping("/join")
    public String createAccount(
            JoinDTO dto,
            Model model
    ){
        ResponseModel response = new ResponseModel();
        response.setTitle("join");
        if (!dto.getPassword().equals(dto.getCorrectPassword())){
            response.setStatusCode(400);
            response.setMessage("비밀번호가 맞지 않습니다.. 다시 시도해주세요🥲");
            model.addAttribute("response", response);
            return "home";
        }
        dto.setProfileImgUrl("/images/kang.png");
        boolean result = dao.insertAccount(dto);
        if (result){
            response.setStatusCode(201);
            response.setMessage("환영합니다! 회원가입이 완료되었습니다.");
        } else {
            response.setStatusCode(500);
            response.setMessage("문제가 생겼어요! 다시 시도해주세요");
        }
        model.addAttribute("response", response);
        return "home";
    }

    @RequestMapping(value = "/check/{checkValue}", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel isValue(
            @PathVariable("checkValue") String checkValue,
            @RequestParam("value") String value
    ){
        int result = dao.getExists(checkValue, value);
        ResponseModel response = new ResponseModel();
        response.setTitle(String.format("%s", checkValue));
        if (result==0) {
            response.setContent(true);
            response.setMessage("OK");
        } else{
            response.setContent(false);
            response.setMessage("It exists");
        }
        return response;
    }

}