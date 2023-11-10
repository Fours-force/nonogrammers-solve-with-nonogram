package com.dottree.nonogrammers.controller;
import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.*;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.service.CommentService;
import com.dottree.nonogrammers.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import com.dottree.nonogrammers.repository.PostRepository;
import com.dottree.nonogrammers.repository.CommentRepository;

@Controller
//@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class PostController {
//    private final PostMapper dao;
//    private final PostRepository repositoryP;
//    private final CommentRepository repositoryC;
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

//    @GetMapping("/post")
//    public ModelAndView list(){
//        List<PostDTO> list= repositoryP.listAllPosts();
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("list",list);
//        mav.setViewName("community");
//        return mav;
//    }
    @GetMapping("/post")
    public String ListAllPosts(Model model) {
        List<PostDTO> list = postService.ListAllPosts();
        model.addAttribute("list", list);
        return "community";
    }

    @GetMapping("/post/notice")
    public String ListNoticePosts(Model model) {
        List<PostDTO> list = postService.ListNoticePosts();
        model.addAttribute("list", list);
        return "community";
    }
    @GetMapping("/post/free")
    public String ListFreePosts(Model model) {
        List<PostDTO> list = postService.ListFreePosts();
        model.addAttribute("list", list);
        return "community";
    }
    @GetMapping("/post/qa")
    public String ListQnAPosts(Model model) {
        List<PostDTO> list = postService.ListQnAPosts();
        model.addAttribute("list", list);
        return "community";
    }
    @GetMapping("/post/nono")
    public String ListShowNonoPosts(Model model) {
        List<PostDTO> list = postService.ListShowNonoPosts();
        model.addAttribute("list", list);
        return "community";
    }
    @GetMapping("/search")
    public String SearchWord(@RequestParam String keyword,Model model) {
        List<PostDTO> list = postService.SearchWord(keyword);
        model.addAttribute("list", list);
        return "community";
    }
    @RequestMapping("/detail")
    @Transactional
    public String detail(@RequestParam("postId") int postId,Model model){
        postService.incrementViewCount(postId);
        PostDTO postDTO = postService.ShowDetailPost(postId);
        List<CommentDTO> list = postService.CommentList(postId);
        List<Integer> counts = commentService.CountByPostId(postId);
        model.addAttribute("pos", postDTO);
        model.addAttribute("comm",list);
        model.addAttribute("counts",counts);
        return "detail";
    }
    @PostMapping("/detailComment")
    @Transactional
    public String detailComment(@ModelAttribute Comment cd,Model model) {
        commentService.InsertComment(cd.getPostId(),cd.getUserId(),cd.getContent());
        PostDTO postDTO= postService.ShowDetailPost(cd.getPostId());
        List<CommentDTO> list= postService.CommentList(cd.getPostId());
        model.addAttribute("pos", postDTO);
        model.addAttribute("comm",list);
        return "redirect:/detail?postId=" + cd.getPostId();
    }
    @PostMapping("/deleteComment")
    @Transactional
    public String deleteComment(@RequestParam int postId,@ModelAttribute Comment cd,Model model){
        commentService.DeleteComment(cd.getCommentId());
        PostDTO postDTO= postService.ShowDetailPost(postId);
        List<CommentDTO> list=postService.CommentList(postId);
        model.addAttribute("pos", postDTO);
        model.addAttribute("comm",list);
        return "redirect:/detail?postId=" + postId;
    }
    @PostMapping("/editComment")
    @Transactional
    public String editComment(@RequestParam int postId,@ModelAttribute Comment cd,Model model){
        commentService.EditComment(cd.getContent(),cd.getCommentId());
        PostDTO postDTO= postService.ShowDetailPost(postId);
        List<CommentDTO> list=postService.CommentList(postId);
        model.addAttribute("pos", postDTO);
        model.addAttribute("comm",list);
        return "redirect:/detail?postId=" + postId;
    }
//    @GetMapping("/post/notice")
//    public ModelAndView listNotice(){
//        List<PostDTO> list= repositoryP.listNoticePosts();
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("list",list);
//        mav.setViewName("community");
//        return mav;
//    }
//    @GetMapping("/post/free")
//    public ModelAndView listFree(){
//        List<PostDTO> list= repositoryP.listFreePosts();
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("list",list);
//        mav.setViewName("community");
//
//        return mav;
//    }
//    @GetMapping("/post/qa")
//    public ModelAndView qa(){
//        List<PostDTO> list= repositoryP.listQnAPosts();
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("list",list);
//        mav.setViewName("community");
//        return mav;
//    }
//    @GetMapping("/post/nono")
//    public ModelAndView nono(){
//        List<PostDTO> list= repositoryP.listShowNonoPosts();
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("list",list);
//        mav.setViewName("community");
//        return mav;
//    }
//    @RequestMapping("/detail")
//    @Transactional
//    public ModelAndView detail(@RequestParam("postId") int postId){
//        ModelAndView mav=new ModelAndView();
//        repositoryP.incrementViewCount(postId);
//        PostDTO pos= repositoryP.showDetailPost(postId);
//        List<CommentDTO> list= repositoryP.commentList(postId);
//        List<Integer> counts = repositoryC.countByPostId(postId);
//        mav.addObject("pos", pos);
//        mav.addObject("comm",list);
//        mav.addObject("counts",counts);
////        mav.addObject("nav", "community" );
//        mav.setViewName("detail");
//        return mav;
//    }
//    @GetMapping("/search")
//    public ModelAndView search(String keyword){
//        ModelAndView mav=new ModelAndView();
//        List<PostDTO> list= repositoryP.searchWord(keyword);
//        mav.addObject("list",list);
//        mav.setViewName("community");
//        return mav;
//    }
//    @PostMapping("/detailComment")
//    public String detailComment(@ModelAttribute Comment cd) {
//        repositoryC.insertComment(cd.getPostId(),cd.getUserId(),cd.getContent());
//        ModelAndView mav=new ModelAndView();
//        PostDTO pos= repositoryP.showDetailPost(cd.getPostId());
//        List<CommentDTO> list=repositoryP.commentList(cd.getPostId());
//        mav.addObject("pos", pos);
//        mav.addObject("comm",list);
//        System.out.println(cd.getPostId());
//        return "redirect:/detail?postId=" + cd.getPostId();
//    }
//
//    @PostMapping("/deleteComment")
//    @Transactional
//    public String deleteComment(@RequestParam int postId,Comment cd){
//        repositoryC.deleteComment(cd.getCommentId());
//        ModelAndView mav=new ModelAndView();
//        PostDTO pos= repositoryP.showDetailPost(postId);
//        List<CommentDTO> list=repositoryP.commentList(postId);
//        mav.addObject("pos", pos);
//        mav.addObject("comm",list);
//        return "redirect:/detail?postId=" + postId;
//    }
//
//    @PostMapping("/editComment")
//    @Transactional
//    public String editComment(@RequestParam int postId,Comment cd){
//        repositoryC.editComment(cd.getContent(),cd.getCommentId());
//        ModelAndView mav=new ModelAndView();
//        PostDTO pos= repositoryP.showDetailPost(postId);
//        List<CommentDTO> list=repositoryP.commentList(postId);
//        mav.addObject("pos", pos);
//        mav.addObject("comm",list);
//        System.out.println(postId);
//        return "redirect:/detail?postId=" + postId;
//    }
    @RequestMapping("/posting")
    @Transactional
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
//    @RequestMapping("/editing")
//    @Transactional
//    public ModelAndView editing(Post pd){
//        ModelAndView mav=new ModelAndView();
//        PostDTO pos= repositoryP.showDetailPost(pd.getPostId());
//        mav.addObject("pos", pos);
//        mav.setViewName("editWrite");
//        return mav;
//    }
////    @PostMapping("/editPosts")
////    public String editPosts(@RequestParam String postId,PostDTO pd){
////        boolean result= dao.editPost(pd);
////        return "redirect:/detail?postId=" + postId;
////    }
////    @PostMapping("/editPosts")
////    public String editPosts(PostDTO postDTO,
////                            UploadImageVO vo,
////                            FileDTO fileDTO,
////                            @RequestParam("category") String category){
////            try{
////                postDTO.setBoardType(dao.getBoardType(category));
////                boolean insertResult = dao.editPost(postDTO);
////                MultipartFile[] uploadImageFiles = vo.getUploadImageFiles();
////
////                if (insertResult && (!uploadImageFiles[0].isEmpty())) {
////                    String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
////                    fileDTO.setPostId(postDTO.getId());
////
////                    for (MultipartFile mfile : uploadImageFiles) {
////                        String filename = UUID.randomUUID().toString();
////                        System.out.println(mfile);
////                        try {
////                            // 파일 정보
////                            String originalFilename = mfile.getOriginalFilename();
////                            String contentType = mfile.getContentType(); // MIME ex) image/jpeg, image/png,
////                            String fileExtension = "." + (contentType.substring(contentType.indexOf("/") + 1));
////
////                            // 파일 저장
////                            File filepath = new File(path + filename + fileExtension);
////                            mfile.transferTo(filepath);
////
////                            // Insert - 파일 테이블
////                            fileDTO.setFilename(originalFilename.substring(0, originalFilename.indexOf("."))); // 클라이언트가 제공한 파일명
////                            fileDTO.setFileExtension(fileExtension);
////                            fileDTO.setFileUrl("/images/post/" + filename + fileExtension);
////                            boolean uploadResult = dao.updateUploadImage(fileDTO);
////
////                        } catch (IOException ioe) {
////                            ioe.printStackTrace();
////                        }
////                    }
////                }
////            } catch(Exception e){
////                e.printStackTrace();
////            }
////            return "redirect:/detail?postId=" + String.valueOf(postDTO.getPostId());
////    }
////
////    @PostMapping("/postDelete")
////    public String postDelete(PostDTO pd){
////        boolean result= dao.deletePost(pd);
////        return "redirect:/post";
////    }
////
//    @PostMapping("/post/write")
//    public String writePost(PostDTO postDTO,
//                            UploadImageVO vo,
//                            FileDTO fileDTO,
//                            @RequestParam("category") String category
//
//    ){
//        try{
//            postDTO.setBoardType(postService.GetBoardType(category));
//            boolean insertResult = postService.InsertPost(postDTO.getBoardType(), postDTO.getUserId(), postDTO.getTitle(), postDTO.getContent());
//            MultipartFile[] uploadImageFiles = vo.getUploadImageFiles();
//
//            if (insertResult && (!uploadImageFiles[0].isEmpty())) {
//                String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
//                fileDTO.setPostId(postDTO.getPostId());
//
//                for (MultipartFile mfile : uploadImageFiles) {
//                    String filename = UUID.randomUUID().toString();
//                    System.out.println(mfile);
//                    try {
//                        // 파일 정보
//                        String originalFilename = mfile.getOriginalFilename();
//                        String contentType = mfile.getContentType(); // MIME ex) image/jpeg, image/png,
//                        String fileExtension = "." + (contentType.substring(contentType.indexOf("/") + 1));
//
//                        // 파일 저장
//                        File filepath = new File(path + filename + fileExtension);
//                        mfile.transferTo(filepath);
//
//                        // Insert - 파일 테이블
//                        fileDTO.setFilename(originalFilename.substring(0, originalFilename.indexOf("."))); // 클라이언트가 제공한 파일명
//                        fileDTO.setFileExtension(fileExtension);
//                        fileDTO.setFileUrl("/images/post/" + filename + fileExtension);
//                        boolean uploadResult = postService.InsertUploadImage(fileDTO.getPostId(), fileDTO.getFilename(), fileDTO.getFileExtension(), fileDTO.getFileUrl());
//
//                    } catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    }
//                }
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        return "redirect:/post";
//    }
//
//    @RequestMapping(value = "/post/like", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
//    @ResponseBody
//    @Transactional
//    public ResponseModel postLike(LikeDTO dto){
//
//        ResponseModel response = new ResponseModel();
//        int isLike = dao.getUserPostLike(dto); // 해당 유저가 해당 게시글을 좋아요 했는지 여부 조회
//
//        if (isLike == 0){ // 0이면, 이제 좋아요를 누른 것이므로 좋아요 등록
//            boolean result = dao.addPostLike(dto);
//            if (result){
//                response.setStatusCode(201);
//            }
//            else {
//                response.setStatusCode(500);
//            }
//        }
//        else { // 1이면, 이미 좋아요를 누른 것이므로 좋아요 취소
//            boolean result = dao.delPostLike(dto);
//            if (result){
//                response.setStatusCode(204);
//            }
//            else {
//                response.setStatusCode(500);
//            }
//        }
//        // 해당 게시글의 좋아요 수
//        int getPostLike = dao.getPostLike(dto.getPostId());
//        HashMap<String, Object> mapData = new HashMap<>();
//        mapData.put("data", getPostLike);
//
//        response.setTitle("Like");
//        response.setMapData(mapData);
//        return response;
//    }
//
    public String isUserIdNullthenRedirect(HttpSession session) {
        if(session.getAttribute("value") == null) {
            System.out.println("************ userId is NULL ************");
            return "redirect:/login";
        } else {
            return "";
        }
    }
}
