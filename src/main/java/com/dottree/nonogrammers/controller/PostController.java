package com.dottree.nonogrammers.controller;
import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.*;
import com.dottree.nonogrammers.entity.Comment;
import com.dottree.nonogrammers.entity.Likes;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.dottree.nonogrammers.repository.PostRepository;
import com.dottree.nonogrammers.repository.CommentRepository;

//import static java.time.LocalDateTime.now;

@RestController
//@Controller
//@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
@CrossOrigin(origins = "*")
public class PostController {
//    private final PostMapper dao;
//    private final PostRepository repositoryP;
//    private final CommentRepository repositoryC;
    private final PostService postService;
    private final CommentService commentService;
    private final FileService fileService;
    private final UserGetService userGetService;
    private final LikesService likesService;
    public PostController(PostService postService, CommentService commentService,FileService fileService,UserGetService userGetService,LikesService likesService) {
        this.postService = postService;
        this.commentService = commentService;
        this.fileService = fileService;
        this.userGetService= userGetService;
        this.likesService=likesService;
    }
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostRequest {
        private PostDTO postDTO;
        private UploadImageVO vo;
        private FileDTO fileDTO;
        private String category;
    }
//    @GetMapping("/post")
//    public ModelAndView list(){
//        List<PostDTO> list= repositoryP.listAllPosts();
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("list",list);
//        mav.setViewName("community");
//        return mav;
//    }
//    @GetMapping("/post")
//    public String ListAllPosts(Model model) {
//        List<PostDTO> list = postService.ListAllPosts();
//        model.addAttribute("list", list);
//        return "community";
//    }
//    @GetMapping("/post")
//    public ResponseEntity<List<PostDTO>> ListAllPosts() {
//        List<PostDTO> list = postService.ListAllPosts();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
    @GetMapping({"/post", "/post/all"})
    public ResponseEntity<Page<PostDTO>> ListAllPosts(Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<PostDTO> list = postService.ListAllPosts(fixedPageable);
        return ResponseEntity.ok(list);
    }

//    @GetMapping("/post/notice")
//    public String ListNoticePosts(Model model) {
//        List<PostDTO> list = postService.ListNoticePosts();
//        model.addAttribute("list", list);
//        return "community";
//    }
    @GetMapping("/post/notice")
    public ResponseEntity<Page<PostDTO>> ListNoticePosts(Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<PostDTO> list = postService.ListNoticePosts(fixedPageable);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/post/free")
    public ResponseEntity<Page<PostDTO>> ListFreePosts(Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<PostDTO> list = postService.ListFreePosts(fixedPageable);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/post/qa")
    public ResponseEntity<Page<PostDTO>> ListQnAPosts(Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<PostDTO> list = postService.ListQnAPosts(fixedPageable);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/post/nono")
    public ResponseEntity<Page<PostDTO>> ListShowNonoPosts(Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<PostDTO> list = postService.ListShowNonoPosts(fixedPageable);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<PostDTO>> SearchWord(@RequestParam String keyword,Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<PostDTO> list = postService.SearchWord(keyword,fixedPageable);
        return ResponseEntity.ok(list);
    }

//    @GetMapping("/search")
//    public String SearchWord(@RequestParam String keyword,Model model) {
//        List<PostDTO> list = postService.SearchWord(keyword);
//        model.addAttribute("list", list);
//        return "community";
//    }
//    @GetMapping("/post/free")
//    public String ListFreePosts(Model model) {
//        List<PostDTO> list = postService.ListFreePosts();
//        model.addAttribute("list", list);
//        return "community";
//    }
//    @GetMapping("/post/qa")
//    public String ListQnAPosts(Model model) {
//        List<PostDTO> list = postService.ListQnAPosts();
//        model.addAttribute("list", list);
//        return "community";
//    }
//    @GetMapping("/post/nono")
//    public String ListShowNonoPosts(Model model) {
//        List<PostDTO> list = postService.ListShowNonoPosts();
//        model.addAttribute("list", list);
//        return "community";
//    }
//    @GetMapping("/search")
//    public String SearchWord(@RequestParam String keyword,Model model) {
//        List<PostDTO> list = postService.SearchWord(keyword);
//        model.addAttribute("list", list);
//        return "community";
//    }
    @RequestMapping("/detail")
    public ResponseEntity<Map<String, Object>> detail(@RequestParam("postId") int postId) {
        postService.incrementViewCount(postId);
        PostDTO postDTO = postService.ShowDetailPost(postId);
        List<CommentDTO> list = commentService.CommentList(postId);
        List<Integer> counts = commentService.CountByPostId(postId);
        List<FileDTO> files = fileService.ListImage(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("postDTO", postDTO);
        response.put("comments", list);
        response.put("counts", counts);
        response.put("files", files);
        return ResponseEntity.ok(response);
    }
//    @RequestMapping("/detail")
//    @Transactional
//    public String detail(@RequestParam("postId") int postId,Model model){
//        postService.incrementViewCount(postId);
//        PostDTO postDTO = postService.ShowDetailPost(postId);
//        List<CommentDTO> list = commentService.CommentList(postId);
//        List<Integer> counts = commentService.CountByPostId(postId);
//        model.addAttribute("pos", postDTO);
//        model.addAttribute("comm",list);
//        model.addAttribute("counts",counts);
//        return "detail";
//    }
    @PostMapping("/detailComment")
    @Transactional
    public ResponseEntity<Map<String, Object>> detailComment(@RequestBody CommentDTO cd) {
//        commentService.InsertComment(cd.getPostId(),cd.getUserId(),cd.getContent());
//
        PostDTO postDTO= postService.ShowDetailPost(cd.getPostId());
        cd.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Comment resultComment = commentService.InsertComment(cd,postDTO.toEntity());
        List<CommentDTO> list= commentService.CommentList(cd.getPostId());
        Map<String, Object> response = new HashMap<>();
        response.put("postDTO", postDTO);
        response.put("comments", list);
        return ResponseEntity.ok(response);
    }
//    @PostMapping("/detailComment")
//    @Transactional
//    public String detailComment(@ModelAttribute Comment cd,Model model) {
//        commentService.InsertComment(cd.getPostId(),cd.getUserId(),cd.getContent());
//        PostDTO postDTO= postService.ShowDetailPost(cd.getPostId());
//        List<CommentDTO> list= commentService.CommentList(cd.getPostId());
//        model.addAttribute("pos", postDTO);
//        model.addAttribute("comm",list);
//        return "redirect:/detail?postId=" + cd.getPostId();
//    }
    @PostMapping("/deleteComment")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody CommentDTO cd) {
        commentService.DeleteComment(cd.getCommentId());
        PostDTO postDTO= postService.ShowDetailPost(cd.getPostId());
        List<CommentDTO> list= commentService.CommentList(cd.getPostId());
        Map<String, Object> response = new HashMap<>();
        response.put("postDTO", postDTO);
        response.put("comments", list);
        return ResponseEntity.ok(response);
    }
//    @PostMapping("/deleteComment")
//    @Transactional
//    public String deleteComment(@RequestParam int postId,@ModelAttribute Comment cd,Model model){
//        commentService.DeleteComment(cd.getCommentId());
//        PostDTO postDTO= postService.ShowDetailPost(postId);
//        List<CommentDTO> list=commentService.CommentList(postId);
//        model.addAttribute("pos", postDTO);
//        model.addAttribute("comm",list);
//        return "redirect:/detail?postId=" + postId;
//    }
    @PostMapping("/editComment")
    @Transactional
    public ResponseEntity<Map<String, Object>> editComment(@RequestBody CommentDTO cd) {
        commentService.EditComment(cd.getContent(),cd.getCommentId());
        PostDTO postDTO= postService.ShowDetailPost(cd.getPostId());
        List<CommentDTO> list= commentService.CommentList(cd.getPostId());
        Map<String, Object> response = new HashMap<>();
        response.put("postDTO", postDTO);
        response.put("comments", list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> editComment(@RequestParam("nickName") String nickName){
        UserDTO userDTOtoFindUserId = userGetService.FindByNickName(nickName);
        return ResponseEntity.ok(userDTOtoFindUserId);
    }
//    @PostMapping("/editComment")
//    @Transactional
//    public String editComment(@RequestParam int postId,@ModelAttribute Comment cd,Model model){
//        commentService.EditComment(cd.getContent(),cd.getCommentId());
//        PostDTO postDTO= postService.ShowDetailPost(postId);
//        List<CommentDTO> list=commentService.CommentList(postId);
//        model.addAttribute("pos", postDTO);
//        model.addAttribute("comm",list);
//        return "redirect:/detail?postId=" + postId;
//    }
////    @GetMapping("/post/notice")
////    public ModelAndView listNotice(){
////        List<PostDTO> list= repositoryP.listNoticePosts();
////        ModelAndView mav=new ModelAndView();
////        mav.addObject("list",list);
////        mav.setViewName("community");
////        return mav;
////    }
////    @GetMapping("/post/free")
////    public ModelAndView listFree(){
////        List<PostDTO> list= repositoryP.listFreePosts();
////        ModelAndView mav=new ModelAndView();
////        mav.addObject("list",list);
////        mav.setViewName("community");
////
////        return mav;
////    }
////    @GetMapping("/post/qa")
////    public ModelAndView qa(){
////        List<PostDTO> list= repositoryP.listQnAPosts();
////        ModelAndView mav=new ModelAndView();
////        mav.addObject("list",list);
////        mav.setViewName("community");
////        return mav;
////    }
////    @GetMapping("/post/nono")
////    public ModelAndView nono(){
////        List<PostDTO> list= repositoryP.listShowNonoPosts();
////        ModelAndView mav=new ModelAndView();
////        mav.addObject("list",list);
////        mav.setViewName("community");
////        return mav;
////    }
////    @RequestMapping("/detail")
////    @Transactional
////    public ModelAndView detail(@RequestParam("postId") int postId){
////        ModelAndView mav=new ModelAndView();
////        repositoryP.incrementViewCount(postId);
////        PostDTO pos= repositoryP.showDetailPost(postId);
////        List<CommentDTO> list= repositoryP.commentList(postId);
////        List<Integer> counts = repositoryC.countByPostId(postId);
////        mav.addObject("pos", pos);
////        mav.addObject("comm",list);
////        mav.addObject("counts",counts);
//////        mav.addObject("nav", "community" );
////        mav.setViewName("detail");
////        return mav;
////    }
////    @GetMapping("/search")
////    public ModelAndView search(String keyword){
////        ModelAndView mav=new ModelAndView();
////        List<PostDTO> list= repositoryP.searchWord(keyword);
////        mav.addObject("list",list);
////        mav.setViewName("community");
////        return mav;
////    }
////    @PostMapping("/detailComment")
////    public String detailComment(@ModelAttribute Comment cd) {
////        repositoryC.insertComment(cd.getPostId(),cd.getUserId(),cd.getContent());
////        ModelAndView mav=new ModelAndView();
////        PostDTO pos= repositoryP.showDetailPost(cd.getPostId());
////        List<CommentDTO> list=repositoryP.commentList(cd.getPostId());
////        mav.addObject("pos", pos);
////        mav.addObject("comm",list);
////        System.out.println(cd.getPostId());
////        return "redirect:/detail?postId=" + cd.getPostId();
////    }
////
////    @PostMapping("/deleteComment")
////    @Transactional
////    public String deleteComment(@RequestParam int postId,Comment cd){
////        repositoryC.deleteComment(cd.getCommentId());
////        ModelAndView mav=new ModelAndView();
////        PostDTO pos= repositoryP.showDetailPost(postId);
////        List<CommentDTO> list=repositoryP.commentList(postId);
////        mav.addObject("pos", pos);
////        mav.addObject("comm",list);
////        return "redirect:/detail?postId=" + postId;
////    }
////
////    @PostMapping("/editComment")
////    @Transactional
////    public String editComment(@RequestParam int postId,Comment cd){
////        repositoryC.editComment(cd.getContent(),cd.getCommentId());
////        ModelAndView mav=new ModelAndView();
////        PostDTO pos= repositoryP.showDetailPost(postId);
////        List<CommentDTO> list=repositoryP.commentList(postId);
////        mav.addObject("pos", pos);
////        mav.addObject("comm",list);
////        System.out.println(postId);
////        return "redirect:/detail?postId=" + postId;
////    }
//    @RequestMapping("/posting")
//    @Transactional
//    public String posting(Model model,
//                          @RequestHeader String referer,
//                          HttpSession session){
//        String redirectLogin = isUserIdNullthenRedirect(session);
//        if(!redirectLogin.equals("")) {
//            return redirectLogin;
//        }
//        model.addAttribute("ref", referer);
//        return "write";
//    }
//    @RequestMapping("/detail")
//    public ResponseEntity<Map<String, Object>> detail(@RequestParam("postId") int postId) {
//        postService.incrementViewCount(postId);
//        PostDTO postDTO = postService.ShowDetailPost(postId);
//        List<CommentDTO> list = commentService.CommentList(postId);
//        List<Integer> counts = commentService.CountByPostId(postId);
//        List<FileDTO> files = fileService.ListImage(postId);
//        Map<String, Object> response = new HashMap<>();
//        response.put("postDTO", postDTO);
//        response.put("comments", list);
//        response.put("counts", counts);
//        response.put("files", files);
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/post/write")
//    @Transactional
    public ResponseEntity<Map<String, Object>> editing(@RequestParam("postId") String postId){
//        ModelAndView mav=new ModelAndView();
        PostDTO postDTO= postService.ShowDetailPost(Integer.parseInt(postId));
        Map<String, Object> response = new HashMap<>();
        response.put("postDTO", postDTO);
//        response.put("comments", list);
//        mav.addObject("pos", pos);
//        mav.setViewName("editWrite");
        return ResponseEntity.ok(response);
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
//////    @PostMapping("/editPosts")
//////    public String editPosts(@RequestParam String postId,PostDTO pd){
//////        boolean result= dao.editPost(pd);
//////        return "redirect:/detail?postId=" + postId;
//////    }
//////    @PostMapping("/editPosts")
//////    public String editPosts(PostDTO postDTO,
//////                            UploadImageVO vo,
//////                            FileDTO fileDTO,
//////                            @RequestParam("category") String category){
//////            try{
//////                postDTO.setBoardType(dao.getBoardType(category));
//////                boolean insertResult = dao.editPost(postDTO);
//////                MultipartFile[] uploadImageFiles = vo.getUploadImageFiles();
//////
//////                if (insertResult && (!uploadImageFiles[0].isEmpty())) {
//////                    String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
//////                    fileDTO.setPostId(postDTO.getId());
//////
//////                    for (MultipartFile mfile : uploadImageFiles) {
//////                        String filename = UUID.randomUUID().toString();
//////                        System.out.println(mfile);
//////                        try {
//////                            // 파일 정보
//////                            String originalFilename = mfile.getOriginalFilename();
//////                            String contentType = mfile.getContentType(); // MIME ex) image/jpeg, image/png,
//////                            String fileExtension = "." + (contentType.substring(contentType.indexOf("/") + 1));
//////
//////                            // 파일 저장
//////                            File filepath = new File(path + filename + fileExtension);
//////                            mfile.transferTo(filepath);
//////
//////                            // Insert - 파일 테이블
//////                            fileDTO.setFilename(originalFilename.substring(0, originalFilename.indexOf("."))); // 클라이언트가 제공한 파일명
//////                            fileDTO.setFileExtension(fileExtension);
//////                            fileDTO.setFileUrl("/images/post/" + filename + fileExtension);
//////                            boolean uploadResult = dao.updateUploadImage(fileDTO);
//////
//////                        } catch (IOException ioe) {
//////                            ioe.printStackTrace();
//////                        }
//////                    }
//////                }
//////            } catch(Exception e){
//////                e.printStackTrace();
//////            }
//////            return "redirect:/detail?postId=" + String.valueOf(postDTO.getPostId());
//////    }
//////
//    @PostMapping("/postDelete")
//    public String postDelete(int postId){
//        postService.DeletePost(postId);
//        return "redirect:/post";
//    }
    @GetMapping("/postDelete")
    @Transactional
    public ResponseEntity<Void> postDelete(@RequestParam int postId) {
        System.out.println(postId);
        postService.DeletePost(postId);
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/post/write",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    @Transactional
    public ResponseEntity<Void> writePost(@RequestPart PostDTO postDTO,
                                          @RequestPart(required = false) MultipartFile[] vo,
                                          @RequestPart FileDTO fileDTO,
                                          @RequestPart String category) {
        LocalDateTime now = LocalDateTime.now();
//        try{
        postDTO.setBoardType(postService.GetBoardType(category));
        postDTO.setCreatedAt(LocalDate.from(now));
        System.out.println("INSERT 실행합니다!");
//        System.out.println(now);
        Post result = postService.InsertPost(postDTO);
        System.out.println("insert 완료");
        System.out.println(result.getTitle());
        System.out.println("insertPostId : " + result.getPostId());

//        postService.savePost(p); // savePost 메서드는 Post 엔터티를 데이터베이스에 저장하는 메서드입니다.
//        System.out.println(postDTO);
//
//        log.info(String.valueOf(postDTO));
//        System.out.println(isInsert);
//        log.info(String.valueOf(isInsert));
//        postService.find
        if ((!vo[0].isEmpty())) {
                String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
                System.out.println("postID : --------------- " + postDTO.getPostId());
                fileDTO.setPostId(result.getPostId());
            System.out.println("fileDTO.getPostId() : "+fileDTO.getPostId());
                for (MultipartFile mfile : vo) {
                    String filename = UUID.randomUUID().toString();
                    System.out.println("filename : "+filename);
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
                        fileDTO.setStatusCode(1);
                        com.dottree.nonogrammers.entity.File resultFile = fileService.InsertFile(fileDTO,result);
//                        postService.InsertUploadImage(fileDTO.getPostId(), fileDTO.getFilename(), fileDTO.getFileExtension(), fileDTO.getFileUrl());

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
    //        return "redirect:/post";
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/post/edit",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> editPost(@RequestPart PostDTO postDTO,
                                          @RequestPart(required = false) MultipartFile[] vo,
                                          @RequestPart FileDTO fileDTO,
                                          @RequestPart String category) {
        LocalDateTime now = LocalDateTime.now();
//        try{
        postDTO.setBoardType(postService.GetBoardType(category));
        postDTO.setCreatedAt(LocalDate.from(now));
        System.out.println("INSERT 실행합니다!");
//        System.out.println(now);
        postService.EditPost(postDTO.getTitle(),postDTO.getContent(),LocalDate.now(),postDTO.getBoardType(),postDTO.getPostId(),postDTO.getUserId());
        System.out.println("insert 완료");

//        postService.savePost(p); // savePost 메서드는 Post 엔터티를 데이터베이스에 저장하는 메서드입니다.
//        System.out.println(postDTO);
//
//        log.info(String.valueOf(postDTO));
//        System.out.println(isInsert);
//        log.info(String.valueOf(isInsert));
//        postService.find
        if ((!vo[0].isEmpty())) {
            String path = System.getProperty("user.dir") + "/src/main/resources/static/images/post/";
            System.out.println("postID : --------------- " + postDTO.getPostId());
            fileDTO.setPostId(postDTO.getPostId());
            System.out.println("fileDTO.getPostId() : "+fileDTO.getPostId());
            for (MultipartFile mfile : vo) {
                String filename = UUID.randomUUID().toString();
                System.out.println("filename : "+filename);
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
                    fileDTO.setStatusCode(1);
                    Post p = postDTO.toEntity();
                    System.out.println(p);
                    com.dottree.nonogrammers.entity.File resultFile = fileService.InsertFile(fileDTO,p);
//                        postService.InsertUploadImage(fileDTO.getPostId(), fileDTO.getFilename(), fileDTO.getFileExtension(), fileDTO.getFileUrl());

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
        //        return "redirect:/post";
        return ResponseEntity.ok().build();
    }
//    @PostMapping("/post/write")
//    @Transactional
//    public ResponseEntity<Void> writePost(PostDTO postDTO,
//                                          UploadImageVO vo,
//                                          FileDTO fileDTO,
//                                          @RequestParam("category") String category) {
//        try{
//            postDTO.setBoardType(postService.GetBoardType(category));
//            postService.InsertPost(postDTO.getBoardType(), postDTO.getUserId(), postDTO.getTitle(), postDTO.getContent());
//            MultipartFile[] uploadImageFiles = vo.getUploadImageFiles();
//
//            if ((!uploadImageFiles[0].isEmpty())) {
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
//                        postService.InsertUploadImage(fileDTO.getPostId(), fileDTO.getFilename(), fileDTO.getFileExtension(), fileDTO.getFileUrl());
//
//                    } catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    }
//                }
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
////        return "redirect:/post";
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/post/write")
//    public String writePost(PostDTO postDTO,
//                            UploadImageVO vo,
//                            FileDTO fileDTO,
//                            @RequestParam("category") String category
//
//    ){
//        try{
//            postDTO.setBoardType(postService.GetBoardType(category));
//            postService.InsertPost(postDTO.getBoardType(), postDTO.getUserId(), postDTO.getTitle(), postDTO.getContent());
//            MultipartFile[] uploadImageFiles = vo.getUploadImageFiles();
//
//            if ((!uploadImageFiles[0].isEmpty())) {
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
//                        postService.InsertUploadImage(fileDTO.getPostId(), fileDTO.getFilename(), fileDTO.getFileExtension(), fileDTO.getFileUrl());
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
////
    @PostMapping("/post/like")
    @Transactional
    public ResponseEntity<Integer> postLike(@RequestBody LikeDTO likeDTO){
        int isLike = likesService.CountByUserIdAndPostId(likeDTO.getUserId(),likeDTO.getPostId());
        UserDTO userDTO = userGetService.FindByUserId(String.valueOf(likeDTO.getUserId()));
        PostDTO postDTO = postService.ShowDetailPost(likeDTO.getPostId());
        if (isLike == 0) {
            Likes likes = likesService.addPostLike(likeDTO,userDTO.toEntity(),postDTO.toEntity());
        } else {
            likesService.DeleteByPostId(likeDTO.getPostId());
        }
        int count = likesService.CountByPostId(likeDTO.getPostId());
        return ResponseEntity.ok(count);
    }
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

//    public String isUserIdNullthenRedirect(HttpSession session) {
//        if(session.getAttribute("value") == null) {
//            System.out.println("************ userId is NULL ************");
//            return "redirect:/login";
//        } else {
//            return "";
//        }
//    }
}
