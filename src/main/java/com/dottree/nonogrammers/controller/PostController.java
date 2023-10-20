package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/post/notice")
    public ModelAndView listNotice(){
        List<PostDTO> list= dao.listN();
        ModelAndView mav=new ModelAndView();
        mav.addObject("list",list);
        mav.setViewName("community");
        return mav;
    }
    @GetMapping("/post/free")
    public ModelAndView listFree(){
        List<PostDTO> list= dao.listF();
        ModelAndView mav=new ModelAndView();
        mav.addObject("list",list);
        mav.setViewName("community");

        return mav;
    }
    @GetMapping("/post/qa")
    public ModelAndView qa(){
        List<PostDTO> list= dao.listQ();
        ModelAndView mav=new ModelAndView();
        mav.addObject("list",list);
        mav.setViewName("community");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView detail(String postId){
        ModelAndView mav=new ModelAndView();
        PostDTO pos= dao.detailss(postId);
        dao.incrementViewCount(postId);
        List<CommentDTO> list= dao.commList(postId);
        List<Integer> counts = dao.counting(postId);
        mav.addObject("pos", pos);
        mav.addObject("comm",list);
        mav.addObject("counts",counts);
        mav.setViewName("detail");
        return mav;
    }
    @GetMapping("/search")
    public ModelAndView search(String keyword){
        ModelAndView mav=new ModelAndView();
        List<PostDTO> list= dao.search(keyword);
        mav.addObject("list",list);
        mav.setViewName("community");
        return mav;
    }
    @PostMapping("/detailComment")
        public String detailComment(CommentDTO cd) {
        boolean result= dao.insertComm(cd);
        ModelAndView mav=new ModelAndView();
        PostDTO pos= dao.detailss(String.valueOf(cd.getPostId()));
        List<CommentDTO> list=dao.commList(String.valueOf(cd.getPostId()));
        mav.addObject("pos", pos);
        mav.addObject("comm",list);
        System.out.println(cd.getPostId());
        return "redirect:/detail?postId=" + cd.getPostId();
    }

    @PostMapping("/post/write")
    public String writePost(PostDTO dto,
                            @RequestParam("category") String category){
        dto.setBoardType(dao.getBoardType(category));
        dao.insertPost(dto);
        return "redirect:/community";
    }

}
