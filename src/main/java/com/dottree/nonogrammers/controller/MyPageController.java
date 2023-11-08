package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.domain.*;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.repository.MyPageRepository;
import com.dottree.nonogrammers.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

@RestController
@Slf4j
public class MyPageController {
//    private final PostMapper postMapper;
//    private final UserMapper userMapper;
//
//    private final MainMapper mainMapper;

    private final MyPageRepository myPageRepository;

    private final MyPageService myPageService;

//    public MyPageController(PostMapper postMapper, UserMapper userMapper, MainMapper mainMapper) {
//        this.postMapper = postMapper;
//        this.userMapper = userMapper;
//        this.mainMapper = mainMapper;
//    }

    public MyPageController(MyPageRepository myPageRepository, MyPageService myPageService) {
        this.myPageRepository = myPageRepository;
        this.myPageService = myPageService;
    }

    /**
     * 유저의 작성한 글 페이지를 보여줍니다.
     * @param userPostVO
     * @param userId
     * @return mypostView
     */
//    @GetMapping("/mypost/{userId}")
//    public String userPostView(@ModelAttribute("userPostVO") UserPostVO userPostVO,
//                               @PathVariable("userId") Integer userId,
//                               HttpSession session) {
//        UserDTO userDTO = userMapper.selectUserByUserId(userId);
//        String redirectLogin = isUserIdNullthenRedirect(session);
//        if(!redirectLogin.equals("")) {
//            return redirectLogin;
//        }
////        isUserIdNullthenRedirect(userId);
//        List<PostDTO> postDtoList = postMapper.selectPostList(userId);
//        userPostVO.setUserDTO(userDTO);
//        userPostVO.setUserId(userId);
//        userPostVO.setUserPostList(postDtoList);
//        return "mypost";
//    }

//    @PutMapping(value = "/api/modify/{userId}/{postId}")
////    @ResponseBody
//    public String modifyUserPost(@ModelAttribute("userPostVO") UserPostVO userPostVO,
//                                  @ModelAttribute PostDTO postDTO,
//                                  Model model,
//                                  @PathVariable("postId") Integer postId,
//                                  @PathVariable("userId") Integer userId) {
//
////        isUserIdNullthenRedirect(userId);
//        boolean result = postMapper.updatePostByPostIdAndUserId(postDTO.getTitle(), postDTO.getContent(), postId, userId);
////        postDTO = postMapper.detailss(String.valueOf(postId));
//        UserDTO userDTO = userMapper.selectUserByUserId(userId);
//        List<PostDTO> postDtoList = postMapper.selectPostList(userId);
//        userPostVO.setUserPostList(postDtoList);
//        userPostVO.setUserDTO(userDTO);
//        userPostVO.setUserId(userId);
//        if(result) {
//            model.addAttribute("msg", "게시글이 수정되었습니다.");
//            model.addAttribute(userPostVO);
//        } else {
//            model.addAttribute("msg", "게시글 수정이 실패했습니다.");
//        }
//
//        return "mypost";
//
//    }

    /**
     * 유저의 계정관리 페이지를 보여줍니다.
     * @param userId
     * @param model
     * @return accountmanageView
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity userAccountManageView(@PathVariable("userId") Long userId,
                                                Model model,
                                                HttpSession session) {



//        Optional<User> userOpt = myPageRepository.findById(userId);
//        if(userOpt.isPresent()) {
//            User user = userOpt.get();
//            map.put("code", 200);
//            map.put("user", user);
//        } else {
//            map.put("code", 404);
//        }

        return null;
    }

    /**
     * 유저의 닉네임을 수정합니다.
     * @param userId
     * @param user
     * @return accountmanageView
     */
    @PatchMapping(value = "/user/nickname/{userId}")
    public ResponseEntity changeUserNickname(@PathVariable("userId") Long userId,
                                                      @RequestBody UserDTO user) {
        try {
            myPageService.updateNickName(userId, user);

        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
        }

        return ResponseEntity.created(URI.create("/user/" + userId)).build();

    }

    @PatchMapping(value = "/user/status/{userId}")
    public ResponseEntity withDrawUser(@PathVariable("userId") Long userId,
                                                HttpSession session) {

        myPageService.updateStatusCode(userId);
        session.removeAttribute("value");

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user/isduplicated")
    public ResponseEntity isDuplicatedNickName(@RequestBody UserDTO userDTO) {
        try {
            User user = myPageService.isDuplicatedNickName(userDTO);
            if(user != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(IllegalArgumentException e) {

            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/api/change-profileimgurl/{userId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity changeProfileImgUrl(@PathVariable Long userId,
                                                       @RequestParam("profileImg") MultipartFile imgFile) {
        File file = myPageService.updateProfileImgUrl(userId, imgFile);

//                userMapper.updateProfileImg(user.getEmail(), f.getAbsolutePath().split("static")[1]);
//                userMapper.selectUserByUserId(user.getUserId());
        try {

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return null;
    }

    @GetMapping("/ingnono/{userId}")
    public String getIngUserNono(@PathVariable("userId") Integer userId,
                                 HttpSession session) {

        return "my-nono";
    }

//    @GetMapping("/ingnono/detail/{userId}/{nonoId}")
//    public String getIngUsernono(@PathVariable("userId") Integer userId,
//                                 @PathVariable("nonoId") Integer nonoId,
//                                 Model model,
//                                 HttpSession session) {
//        String redirectLogin = isUserIdNullthenRedirect(session);
//        if(!redirectLogin.equals("")) {
//            return redirectLogin;
//        }
//
//        UserNonoVO userNonoVO = userMapper.selectIngUserNonoDetail(userId, nonoId);
//        if(userNonoVO == null) {
//            // 유효성검사?
//        }
//        model.addAttribute("userNono", userNonoVO);
//
//        return "usernono-detail";
//    }

//    @GetMapping("/solvednono/{userId}")
//    public String getSolvedUserNono(@PathVariable("userId") Integer userId,
//                                    Model model,
//                                    HttpSession session) {
//        String redirectLogin = isUserIdNullthenRedirect(session);
//        if(!redirectLogin.equals("")) {
//            return redirectLogin;
//        }
//        List<UserNonoVO> userNonnolist = userMapper.selectUserNonoByIsSolved(userId, 2);
//        model.addAttribute("title", "내가 푼 노노들");
//        model.addAttribute("isSolved", 1);
//        model.addAttribute("nonoList", userNonnolist);
////        System.out.println(userNonnolist.size());
//        return "my-nono";
//    }

//    @GetMapping("/solvednono/detail/{userId}/{nonoId}")
//    public String getSolvedUsernono(@PathVariable("userId") Integer userId,
//                                    @PathVariable("nonoId") Integer nonoId,
//                                    Model model,
//                                    HttpSession session) {
//        String redirectLogin = isUserIdNullthenRedirect(session);
//        if(!redirectLogin.equals("")) {
//            return redirectLogin;
//        }
//        UserNonoVO userNonoVO = userMapper.selectSolvedUserNonoDetail(userId, nonoId);
//        if(userNonoVO == null) {
//            // 유효성검사?
//        }
//        model.addAttribute("userNono", userNonoVO);
//
//        return "usernono-detail";
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
