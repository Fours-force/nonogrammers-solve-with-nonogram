package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.dao.UserMapper;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.domain.UserDTO;
import com.dottree.nonogrammers.domain.UserPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MyPageController {
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/mypost")
    public String userPostView(@ModelAttribute("userPostVO") UserPostVO userPostVO) {
        List<PostDTO> postDtoList = postMapper.selectPostList(1);
        userPostVO.setUserId(1);
        userPostVO.setUserPostList(postDtoList);
        //model.addAttribute(userPostVO);
        return "mypost";
    }

    @GetMapping("/user/{id}")
    public String userAccountManageView(@PathVariable("id") int userId
            , Model model) {
        UserDTO userDTO = userMapper.selectUserByUserId(1);
        model.addAttribute(userDTO);
        return "accountmanage";
    }
}
