package com.dottree.nonogrammers.controller;
import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.domain.UploadImageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.dottree.nonogrammers.domain.PostDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
        dao.incrementViewCount(postId);
        PostDTO pos= dao.detailss(postId);
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
    @RequestMapping("/posting")
    public String posting(){
        return "write";
    }
    @PostMapping("/post/write")
    public String writePost(PostDTO postDTO,
                            UploadImageVO vo,
                            FileDTO fileDTO,
                            @RequestParam("category") String category

    ){
        try{
            postDTO.setBoardType(dao.getBoardType(category));
            boolean insertResult = dao.insertPost(postDTO);

            if (insertResult && (vo.getUploadImageFiles() != null)) {
                String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
                fileDTO.setPostId(postDTO.getId());
                for (MultipartFile mfile : vo.getUploadImageFiles()) {
                    String filename = UUID.randomUUID().toString();
                    try {
                        // 파일 정보
                        String originalFilename = mfile.getOriginalFilename();
                        String contentType = mfile.getContentType(); // MIME ex) image/jpeg, image/png,
                        String fileExtension = "." + (contentType.substring(contentType.indexOf("/") + 1));

                        // 파일 저장
                        File filepath = new File(path + filename + fileExtension);
                        mfile.transferTo(filepath);

                        // Insert - 파일 테이블
                        fileDTO.setFilename(originalFilename.substring(0, originalFilename.indexOf("."))); // 클라이언트가 제공한 파일명
                        fileDTO.setFileExtension(fileExtension);
                        fileDTO.setFileUrl("/images/post/" + filename + fileExtension);
                        boolean uploadResult = dao.insertUploadImage(fileDTO);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/post";
    }

}
