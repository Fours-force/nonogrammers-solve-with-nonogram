package com.dottree.nonogrammers.controller;

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
