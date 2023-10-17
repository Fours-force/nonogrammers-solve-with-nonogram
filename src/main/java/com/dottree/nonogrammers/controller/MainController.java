package com.dottree.nonogrammers.controller;


import com.dottree.nonogrammers.dao.MainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    final MainMapper mdao;
    public MainController(MainMapper mdao) {
        this.mdao = mdao;
    }

    @RequestMapping("/nono")
    public ModelAndView showNono(int userId, int nonoId){
        ModelAndView mav = new ModelAndView();
        if(mdao.selectUserFromUserNono(userId,nonoId) == 0){
            mdao.insertUserNono(userId,nonoId);
        }

    return mav;

    }
}
