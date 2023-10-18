package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.dottree.nonogrammers.domain.PostDTO;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    @Autowired
    PostMapper dao;
    @GetMapping("/post")
    public ModelAndView list(){
        List<PostDTO> list= dao.listm();
        ModelAndView mav=new ModelAndView();
        mav.addObject("list",list);
        mav.setViewName("community");
        return mav;
    }


}
