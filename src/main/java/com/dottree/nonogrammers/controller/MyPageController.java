package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.domain.*;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.entity.UserNono;
import com.dottree.nonogrammers.repository.MyPageRepository;
import com.dottree.nonogrammers.service.MyPageService;
import com.dottree.nonogrammers.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class MyPageController {

    private final MyPageService myPageService;

    private final PostService postService;

    public MyPageController(MyPageService myPageService, PostService postService) {
        this.myPageService = myPageService;
        this.postService = postService;
    }

    /**
     * 유저의 작성한 글 페이지를 보여줍니다.
     * @param userId
     * @return mypostView
     */
    @GetMapping("/post/{userId}")
    public ResponseEntity userPostList(@PathVariable("userId") Integer userId) {
        try {
            return ResponseEntity.ok(postService.getUserPostList(userId));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 유저의 게시물을 조회하는데 실패하였습니다.");
        }
    }

    /**
     * 유저의 게시물을 수정합니다.
     * @param postId
     * @param userId
     * @param postDTO
     * @return ResponseEntity
     */
    @PatchMapping(value = "/post/{userId}/{postId}")
    public ResponseEntity userPostModify(@PathVariable("postId") Integer postId,
                                         @PathVariable("userId") Integer userId,
                                         @RequestBody PostDTO postDTO) {
        try {
            postService.changeUserPost(postDTO.getTitle(), postDTO.getContent(), postId, userId);
            // TODO: 11/14/23 - 수정 후 리턴 할 객체 무엇 다시 Post 조회 후 list 반환? 아니면 변경된 Post만 반환?
            return ResponseEntity.created(URI.create("/post/"+ userId)).body("게시물 수정에 성공하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물 수정에 실패하였습니다.");
        }

    }

    /**
     * 유저의 계정관리 페이지를 보여줍니다.
     * @param nickName
     * @return ResponseEntity
     */
    @GetMapping("/user/{nickName}")
    public ResponseEntity userAccountManageView(@PathVariable("nickName") String nickName) {
        try {
            return ResponseEntity.ok(myPageService.getUser(nickName));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 유저를 찾는데 실패하였습니다.");
        }
    }

    /**
     * 유저의 닉네임 중복여부를 확인합니다.
     * @param userDTO
     * @return ResponseEntity
     */
    @PostMapping(value = "/user/nickname/isduplicated")
    public ResponseEntity isDuplicatedNickName(@RequestBody UserDTO userDTO) {
        try {
            User user = myPageService.isDuplicatedNickName(userDTO.getNickName());
            if (user == null) {
                return ResponseEntity.ok(userDTO.getNickName());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("닉네임 중복검사에 실패하였습니다.");
            }
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("닉네임 중복검사에 실패하였습니다.");
        }
    }

    /**
     * 유저의 닉네임을 수정합니다.
     * @param userId
     * @param userDTO
     * @return ResponseEntity
     */
    @PatchMapping(value = "/user/nickname/{userId}")
    public ResponseEntity changeUserNickname(@PathVariable("userId") Integer userId,
                                                     @RequestBody UserDTO userDTO) {
        System.out.println(userId + " , " + userDTO.getNickName());
        try {
            myPageService.updateNickName(userId, userDTO);
            return ResponseEntity.created(URI.create("/user/" + userId)).body(myPageService.user(userId));

        } catch (Exception e) { // IllegalArgumentException
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("닉네임 수정에 실패하였습니다.");
        }
    }

    /**
     * 유저의 탈퇴여부 코드를 변경합니다.
     * @param userId
     * @param session
     * @return ResponseEntity
     */
    @PatchMapping(value = "/user/status/{userId}")
    public ResponseEntity<String> withDrawUser(@PathVariable("userId") Integer userId,
                                               HttpSession session) {
        try {
            myPageService.updateStatusCode(userId);
            session.removeAttribute("value");
            return ResponseEntity.ok("회원 상태코드 수정에 성공하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 상태코드 수정에 실패하였습니다.");
        }

    }

    /**
     * 프로필이미지 변경
     * @param imgFile
     * @param userId
     * @return HashMap
     */
    @PostMapping(value = "/user/profileimg/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity changeProfileImgUrl(@PathVariable("userId") Integer userId,
                                              @RequestParam(value="profileImg") MultipartFile imgFile) {
        File file = null;
        try {
            UserDTO userDTO = myPageService.updateProfileImgUrl(userId, imgFile);
//            System.out.println(file.getAbsolutePath());
            System.out.println(userDTO.getProfileImgUrl());
//            return ResponseEntity.ok(file.getAbsolutePath().split("profile")[1]);
            return ResponseEntity.ok(userDTO);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로필 이미지 변경에 실패했습니다.");
        }
    }

    /**
     * 유저의 노노들을 보여줍니다.
     * @param userId
     * @param isSolved
     * @return ResponseEntity
     */
    @GetMapping("/nono/{userId}/{isSolved}")
    public ResponseEntity getIngUserNono(@PathVariable("userId") Integer userId,
                                         @PathVariable("isSolved") Integer isSolved) {
        List<UserNono> userNonnolist = null;
        try {
            userNonnolist = myPageService.getUserNoNoList(userId, isSolved);
            return ResponseEntity.ok(userNonnolist);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저의 NoNo들을 조회하는데 실패하였습니다.");
        }
    }

    /**
     * 유저가 클릭한 노노를 보여줍니다.
     * @param userId
     * @param nonoId
     * @return ResponseEntity
     */
    @GetMapping("/nono/detail/{userId}/{nonoId}/{isSolved}")
    public ResponseEntity getIngUserNoNoDetail(@PathVariable("userId") Integer userId,
                                               @PathVariable("nonoId") Integer nonoId,
                                               @PathVariable("isSolved") Integer isSolved) {
        UserNono userNono = null;
        try {
            userNono = myPageService.getUserNonoDetail(userId, nonoId, isSolved);
            return ResponseEntity.ok(userNono);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저의 NoNo를 조회하는데 실패하였습니다.");
        }
    }
}
