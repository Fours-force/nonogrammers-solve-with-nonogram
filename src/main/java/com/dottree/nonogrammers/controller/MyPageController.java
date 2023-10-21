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
     * @param userPostVO
     * @param session
     * @param userId
     * @return mypostView
     */
    @GetMapping("/mypost/{userId}")
    public String userPostView(@ModelAttribute("userPostVO") UserPostVO userPostVO,
                               HttpSession session,
                               @PathVariable("userId") int userId) {
        List<PostDTO> postDtoList = postMapper.selectPostList(1);
        userPostVO.setUserId(1);
        userPostVO.setUserPostList(postDtoList);
        // 세션에 UserId set
        session.setAttribute("userId", 1);

        return "mypost";
    }

    @PutMapping(value = "/api/modify/{userId}/{postId}")
//    @ResponseBody
    public String modifyUserPost(@ModelAttribute("userPostVO") UserPostVO userPostVO,
                                  @ModelAttribute PostDTO postDTO,
                                  Model model,
                                  @PathVariable("postId") int postId,
                                  @PathVariable("userId") int userId,
                                 HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        //postDTO = postMapper.detailss(String.valueOf(postId));
        //System.out.println(postDTO.getTitle() + "  " + postDTO.getContent() + " " + userId + " " + postId);

        boolean result = postMapper.updatePostByPostIdAndUserId(postDTO.getTitle(), postDTO.getContent(), postId, userId);
//        postDTO = postMapper.detailss(String.valueOf(postId));
        List<PostDTO> postDtoList = postMapper.selectPostList(userId);
        userPostVO.setUserPostList(postDtoList);
        userPostVO.setUserId(userId);
        session.setAttribute("userId", 1);
        if(result) {
            model.addAttribute(userPostVO);
        }

        return "mypost";

    }

    /**
     * 유저의 계정관리 페이지를 보여줍니다.
     * @param userId
     * @param model
     * @param session
     * @return accountmanageView
     */
    @GetMapping("/user/{userId}")
    public String userAccountManageView(@PathVariable("userId") int userId,
                                        Model model,
                                        HttpSession session) {
        UserDTO userDTO = userMapper.selectUserByUserId(1);
        model.addAttribute(userDTO);
        session.setAttribute("userId", 1); //userId

        return "accountmanage";
    }

    /**
     * 유저의 닉네임을 수정합니다.
     * @param userId
     * @param userDTO
     * @param session
     * @return accountmanageView
     */
    @PutMapping(value = "/api/reset-nickname/{userId}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> changeUserNickname(@PathVariable("userId") int userId,
                                                      @RequestBody UserDTO userDTO,
                                                      HttpSession session
    ) {
        HashMap<String, Object> map = new HashMap<>();
        UserDTO user = userMapper.selectUserByUserId(1); // session.getUserId()
        //System.out.println(nickName);
        if(user != null) {
            user.setNickName(userDTO.getNickName());
            userMapper.resetNickname(user.getEmail(), user.getNickName());
            user = userMapper.selectUserByUserId(1);
            map.put("result", 200);
            map.put("obj", user);
        } else {
            map.put("result", 404);
            map.put("msg", "유저 정보를 찾을 수 없습니다.");
        }

        return map;
    }

    @PutMapping(value = "/api/withdraw-user/{userId}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> withDrawUser(@PathVariable("userId") int userId,
                                                @RequestBody UserDTO userDTO,
                                                HttpSession session) {

        HashMap<String, Object> map = new HashMap<>();
        //session에 로그인되어 있는 사용자로 select 해오고
        UserDTO user = userMapper.selectUserByUserId(userId); // session.getUserId()
        //사용자가 입력한 email과 같으면 delete
        UserDTO inputUser = userMapper.selectUserByUserId(userDTO.getUserId());
        if(user != null && user.getEmail().equals(inputUser.getEmail())) {
            userMapper.updateStatusCode(user.getEmail(), 0);
            //session.removeAttribute();
            map.put("result", 200);
            map.put("msg", "탈퇴가 정상적으로 처리되었습니다."); //logout처리? session remove처리?
        } else {
            map.put("result", 404);
            map.put("msg", "유저 정보를 찾을 수 없습니다.");
        }

        return map;
    }

    @PostMapping(value = "/api/isduplicated-nickname/{nickName}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> isDuplicatedNickName(@PathVariable("nickName") String nickName,
                                                        @RequestBody UserDTO userDTO,
                                                        HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        int checkedVal = userMapper.getExists("nickName", userDTO.getNickName());

        if(checkedVal < 1 && !userDTO.getNickName().equals("null")) {
            //session.removeAttribute();
            map.put("result", 200);
            map.put("msg", "사용가능한 닉네임입니다.");
        } else {
            map.put("result", 404);
            map.put("msg", "이미 있는 닉네임입니다.");
        }

        return map;
    }

    @RequestMapping(value = "/api/change-profileimgurl/{userId}", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    @ResponseBody
    public HashMap<String, Object>  changeProfileImgUrl(
                                        @RequestParam("profileImg") MultipartFile imgFile
                                      , @PathVariable int userId
                                       ) {
        HashMap<String, Object> map = new HashMap<>();
        UserDTO user = userMapper.selectUserByUserId(userId);
        if(user == null) {

        }
        byte[] content = null;
        String fileName =  imgFile.getOriginalFilename();
        try {
            content = imgFile.getBytes();
            Path directoryPath = Paths.get("/Users/jasonmilian/Downloads/nonogrammers/src/main/resources/static/images/profile/"+userId);
            try {
                // 디렉토리 생성
                Files.createDirectory(directoryPath);
            } catch (FileAlreadyExistsException e) {
                System.out.println("디렉토리가 이미 존재합니다");
            }
            File f = new File("/Users/jasonmilian/Downloads/nonogrammers/src/main/resources/static/images/profile/"+userId+"/"+fileName);

            if ( f.exists() ) {
                map.put("result", 404);
                map.put("msg", "같은 프로필 사진입니다. 다른 사진을 선택해주세요.");
            } else {
                userMapper.updateProfileImg(user.getEmail(), f.getAbsolutePath().split("static")[1]);
                userMapper.selectUserByUserId(1);
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content);
                fos.close();
                map.put("result", 200);
                map.put("msg", "프로필 사진이 변경되었습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("result", 404);
            map.put("msg", fileName + " : 파일이 이미 존재해요!!");
        }
        return map;
    }

    @GetMapping("/ingnono/{userId}")
    public String getIngUserNono(@PathVariable("userId") int userId,
                                 Model model) {
//        List<UserNonoDTO> nonoList = mainMapper.selectUserNono(userId);
        List<UserNonoVO> userNonnolist = userMapper.selectUserNonoByIsSolved(userId, 0);
//        System.out.println(userNonnolist.get(0).getUserNonoId());
        model.addAttribute("isSolved", 0);
        model.addAttribute("nonoList", userNonnolist);
        return "my-nono";
    }

    @GetMapping("/ingnono/detail/{userId}/{nonoId}")
    public String getIngUsernono(@PathVariable("userId") int userId,
                                 @PathVariable("nonoId") int nonoId,
                                 Model model) {
        UserNonoVO userNonoVO = userMapper.selectIngUserNonoDetail(userId, nonoId);
        if(userNonoVO == null) {
            // 유효성검사?
        }
//        System.out.println(userNonoVO.getUserId());
        model.addAttribute("userNono", userNonoVO);

        return "usernono-detail";
    }

    @GetMapping("/solvednono/{userId}")
    public String getSolvedUserNono(@PathVariable("userId") int userId,
                                 Model model) {
//        List<UserNonoDTO> nonoList = mainMapper.selectUserNono(userId);
        List<UserNonoVO> userNonnolist = userMapper.selectUserNonoByIsSolved(userId, 1);
        System.out.println(userNonnolist.size());
        System.out.println(userNonnolist.get(0).getUserNonoId());
        model.addAttribute("isSolved", 1);
        model.addAttribute("nonoList", userNonnolist);
        return "my-nono";
    }

    @GetMapping("/solvednono/detail/{userId}/{nonoId}")
    public String getSolvedUsernono(@PathVariable("userId") int userId,
                                 @PathVariable("nonoId") int nonoId,
                                 Model model) {
        UserNonoVO userNonoVO = userMapper.selectSolvedUserNonoDetail(userId, nonoId);
        if(userNonoVO == null) {
            // 유효성검사?
        }
//        System.out.println(userNonoVO.getUserId());
        model.addAttribute("userNono", userNonoVO);

        return "usernono-detail";
    }
}