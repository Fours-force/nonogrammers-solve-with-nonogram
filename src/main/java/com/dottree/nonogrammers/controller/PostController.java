package com.dottree.nonogrammers.controller;
import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
        mav.addObject("nav", "community" );
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
    @GetMapping("/post/nono")
    public ModelAndView nono(){
        List<PostDTO> list= dao.listNN();
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
        mav.addObject("nav", "community" );
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
    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam String postId,CommentDTO cd){
        boolean result= dao.deleteComm(cd);
        ModelAndView mav=new ModelAndView();
        PostDTO pos= dao.detailss(postId);
        List<CommentDTO> list=dao.commList(postId);
        mav.addObject("pos", pos);
        mav.addObject("comm",list);
        return "redirect:/detail?postId=" + postId;
    }
    @PostMapping("/editComment")
    public String editComment(@RequestParam String postId,CommentDTO cd){
        boolean result= dao.editComm(cd);
        ModelAndView mav=new ModelAndView();
        PostDTO pos= dao.detailss(postId);
        List<CommentDTO> list=dao.commList(postId);
        mav.addObject("pos", pos);
        mav.addObject("comm",list);
        System.out.println(postId);
        return "redirect:/detail?postId=" + postId;
    }
    @RequestMapping("/posting")
    public String posting(Model model,
                          @RequestHeader String referer,
                          HttpSession session){
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }
        model.addAttribute("ref", referer);
        return "write";
    }

    @PostMapping("/postDelete")
    public String postDelete(PostDTO pd){
        boolean result= dao.deletePost(pd);
        return "redirect:/post";
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
            MultipartFile[] uploadImageFiles = vo.getUploadImageFiles();

            if (insertResult && (!uploadImageFiles[0].isEmpty())) {
                String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
                fileDTO.setPostId(postDTO.getId());

                for (MultipartFile mfile : uploadImageFiles) {
                    String filename = UUID.randomUUID().toString();
                    System.out.println(mfile);
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

    @RequestMapping(value = "/post/like", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel postLike(LikeDTO dto){

        ResponseModel response = new ResponseModel();
        int isLike = dao.getUserPostLike(dto); // 해당 유저가 해당 게시글을 좋아요 했는지 여부 조회

        if (isLike == 0){ // 0이면, 이제 좋아요를 누른 것이므로 좋아요 등록
            boolean result = dao.addPostLike(dto);
            if (result){
                response.setStatusCode(201);
            }
            else {
                response.setStatusCode(500);
            }
        }
        else { // 1이면, 이미 좋아요를 누른 것이므로 좋아요 취소
            boolean result = dao.delPostLike(dto);
            if (result){
                response.setStatusCode(204);
            }
            else {
                response.setStatusCode(500);
            }
        }
        // 해당 게시글의 좋아요 수
        int getPostLike = dao.getPostLike(dto.getPostId());
        HashMap<String, Object> mapData = new HashMap<>();
        mapData.put("data", getPostLike);

        response.setTitle("Like");
        response.setMapData(mapData);
        return response;
    }

    public String isUserIdNullthenRedirect(HttpSession session) {
        if(session.getAttribute("value") == null) {
            System.out.println("************ userId is NULL ************");
            return "redirect:/login";
        } else {
            return "";
        }
    }
}
