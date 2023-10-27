package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.MainMapper;
import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.dao.UserMapper;
import com.dottree.nonogrammers.domain.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
public class MyPageController {
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    private final MainMapper mainMapper;
    public MyPageController(PostMapper postMapper, UserMapper userMapper, MainMapper mainMapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;
        this.mainMapper = mainMapper;
    }

    /**
     * 유저의 작성한 글 페이지를 보여줍니다.
     * @param userPostDTO
     * @param userId
     * @return mypostView
     */
    @GetMapping("/mypost/{userId}")
    public String userPostView(@ModelAttribute("userPostVO") UserPostDTO userPostDTO,
                               @PathVariable("userId") Integer userId,
                               HttpSession session) {
        UserDTO userDTO = userMapper.selectUserByUserId(userId);
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }
//        isUserIdNullthenRedirect(userId);
        List<PostDTO> postDtoList = postMapper.selectPostList(userId);
        userPostDTO.setUserDTO(userDTO);
        userPostDTO.setUserId(userId);
        userPostDTO.setUserPostList(postDtoList);
        return "mypost";
    }

    @PutMapping(value = "/api/modify/{userId}/{postId}")
//    @ResponseBody
    public String modifyUserPost(@ModelAttribute("userPostVO") UserPostDTO userPostDTO,
                                  @ModelAttribute PostDTO postDTO,
                                  Model model,
                                  @PathVariable("postId") Integer postId,
                                  @PathVariable("userId") Integer userId) {

//        isUserIdNullthenRedirect(userId);
        boolean result = postMapper.updatePostByPostIdAndUserId(postDTO.getTitle(), postDTO.getContent(), postId, userId);
//        postDTO = postMapper.detailss(String.valueOf(postId));
        UserDTO userDTO = userMapper.selectUserByUserId(userId);
        List<PostDTO> postDtoList = postMapper.selectPostList(userId);
        userPostDTO.setUserPostList(postDtoList);
        userPostDTO.setUserDTO(userDTO);
        userPostDTO.setUserId(userId);
        if(result) {
            model.addAttribute("msg", "게시글이 수정되었습니다.");
            model.addAttribute(userPostDTO);
        } else {
            model.addAttribute("msg", "게시글 수정이 실패했습니다.");
        }

        return "mypost";

    }

    /**
     * 유저의 계정관리 페이지를 보여줍니다.
     * @param userId
     * @param model
     * @return accountmanageView
     */
    @GetMapping("/user/{userId}")
    public String userAccountManageView(@PathVariable("userId") Integer userId,
                                        Model model,
                                        HttpSession session) {
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }

        UserDTO userDTO = userMapper.selectUserByUserId(userId);
        model.addAttribute(userDTO);

        return "accountmanage";
    }

    /**
     * 유저의 닉네임을 수정합니다.
     * @param userId
     * @param userDTO
     * @return accountmanageView
     */
    @PutMapping(value = "/api/reset-nickname/{userId}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> changeUserNickname(@PathVariable("userId") Integer userId,
                                                      @RequestBody UserDTO userDTO) {
        HashMap<String, Object> map = new HashMap<>();
        UserDTO user = userMapper.selectUserByUserId(userId);
        if(user != null) {
            user.setNickName(userDTO.getNickName());
            userMapper.resetNickname(user.getEmail(), user.getNickName());
            user = userMapper.selectUserByUserId(user.getUserId());
            map.put("result", 200);
            map.put("obj", user);
        } else {
            map.put("result", 404);
            map.put("msg", "유저 정보를 찾을 수 없습니다.");
        }

        return map;
    }

    /**
     * 회원탈퇴
     * @param userId
     * @param session
     * @return HashMap
     */
    @PutMapping(value = "/api/withdraw-user/{userId}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> withDrawUser(@PathVariable("userId") Integer userId,
                                                HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        UserDTO user = userMapper.selectUserByUserId(userId);
        UserDTO inputUser = userMapper.selectUserByUserId(user.getUserId());

        if(user.getEmail() != null && user.getEmail().equals(inputUser.getEmail())) {
            userMapper.updateStatusCode(user.getEmail(), 0);
            map.put("result", 200);
            map.put("msg", "탈퇴가 정상적으로 처리되었습니다.");
            session.removeAttribute("value");

        } else {
            map.put("result", 404);
            map.put("msg", "유저 정보를 찾을 수 없습니다.");
        }

        return map;
    }

    /**
     * 닉네임 중복확인
     * @param nickName
     * @param userDTO
     * @return HashMap
     */
    @PostMapping(value = "/api/isduplicated-nickname/{nickName}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> isDuplicatedNickName(@PathVariable("nickName") String nickName,
                                                        @RequestBody UserDTO userDTO) {
        HashMap<String, Object> map = new HashMap<>();
        int checkedVal = userMapper.getExists("nickName", userDTO.getNickName());

        if(checkedVal < 1 && !userDTO.getNickName().equals("null")) {
            map.put("result", 200);
            map.put("msg", "사용가능한 닉네임입니다.");
        } else {
            map.put("result", 404);
            map.put("msg", "이미 있는 닉네임입니다.");
        }

        return map;
    }

    /**
     * 프로필이미지 변경
     * @param imgFile
     * @param userId
     * @return HashMap
     */
    @RequestMapping(value = "/api/change-profileimgurl/{userId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    @ResponseBody
    public HashMap<String, Object> changeProfileImgUrl(@RequestParam("profileImg") MultipartFile imgFile,
                                                        @PathVariable Integer userId) {
        HashMap<String, Object> map = new HashMap<>();
        UserDTO user = userMapper.selectUserByUserId(userId);
        if(imgFile != null) {
            String fileName =  imgFile.getOriginalFilename();
            try {
                Path directoryPath = Paths.get("/Users/jasonmilian/Downloads/nonogrammers/src/main/resources/static/images/profile/"+userId);
                try {
                    // 디렉토리 생성
                    Files.createDirectory(directoryPath);
                } catch (FileAlreadyExistsException e) {
                    System.out.println("디렉토리가 이미 존재합니다");
                }
                UUID uuid = UUID.randomUUID();
                String ext = fileName.split("\\.")[1];
                fileName = uuid.toString() + "_" + user.getNickName() + "." + ext;

                File f = null;
                f = new File("/Users/jasonmilian/Downloads/nonogrammers/src/main/resources/static/images/profile/"+userId+"/"+fileName);

                if ( f.exists() ) {
                    map.put("result", 404);
                    map.put("msg", "같은 프로필 사진입니다. 다른 사진을 선택해주세요.");
                } else {
                    userMapper.updateProfileImg(user.getEmail(), f.getAbsolutePath().split("static")[1]);
                    userMapper.selectUserByUserId(user.getUserId());

                    Path savePath = Paths.get(f.getAbsolutePath());
                    imgFile.transferTo(savePath);

                    map.put("result", 200);
                    map.put("msg", "프로필 사진이 변경되었습니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                map.put("result", 404);
                map.put("msg", fileName + " : 파일이 이미 존재해요!!");
            }
        } else {
            map.put("result", 404);
            map.put("msg", "파일이 선택되지 않았습니다.");
        }

        return map;
    }

    @GetMapping("/ingnono/{userId}")
    public String getIngUserNono(@PathVariable("userId") Integer userId,
                                 Model model,
                                 HttpSession session) {
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }

        List<UserNonoVO> userNonnolist = userMapper.selectUserNonoByIsSolved(userId, 1);
        model.addAttribute("title", "내가 풀고 있는 노노들");
        model.addAttribute("isSolved", 0);
        model.addAttribute("nonoList", userNonnolist);
        return "my-nono";
    }

    @GetMapping("/ingnono/detail/{userId}/{nonoId}")
    public String getIngUsernono(@PathVariable("userId") Integer userId,
                                 @PathVariable("nonoId") Integer nonoId,
                                 Model model,
                                 HttpSession session) {
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }

        UserNonoVO userNonoVO = userMapper.selectIngUserNonoDetail(userId, nonoId);
        if(userNonoVO == null) {
            // 유효성검사?
        }
        model.addAttribute("userNono", userNonoVO);

        return "usernono-detail";
    }

    @GetMapping("/solvednono/{userId}")
    public String getSolvedUserNono(@PathVariable("userId") Integer userId,
                                    Model model,
                                    HttpSession session) {
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }
        List<UserNonoVO> userNonnolist = userMapper.selectUserNonoByIsSolved(userId, 2);
        model.addAttribute("title", "내가 푼 노노들");
        model.addAttribute("isSolved", 1);
        model.addAttribute("nonoList", userNonnolist);
//        System.out.println(userNonnolist.size());
        return "my-nono";
    }

    @GetMapping("/solvednono/detail/{userId}/{nonoId}")
    public String getSolvedUsernono(@PathVariable("userId") Integer userId,
                                    @PathVariable("nonoId") Integer nonoId,
                                    Model model,
                                    HttpSession session) {
        String redirectLogin = isUserIdNullthenRedirect(session);
        if(!redirectLogin.equals("")) {
            return redirectLogin;
        }
        UserNonoVO userNonoVO = userMapper.selectSolvedUserNonoDetail(userId, nonoId);
        if(userNonoVO == null) {
            // 유효성검사?
        }
        model.addAttribute("userNono", userNonoVO);

        return "usernono-detail";
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
