package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.UserDTO;
import com.dottree.nonogrammers.domain.UserNonoVO;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.repository.MyPageRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageService(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

    /* public User selectUser(Long userId) {

        return myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
    } */
    @Transactional
    public void updateNickName(Long userId, UserDTO userDTO) {
        // DB에서 조회한다.
        User user = myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
        // 조회한 유저의 닉네임 수정
        user = user.toBuilder()
                .nickName(userDTO.getNickName())
                .build();

        myPageRepository.save(user);
    }

    @Transactional
    public void updateStatusCode(Long userId) {
        User user = myPageRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));

        user = user.toBuilder()
                .statusCode(0)
                .build();

        myPageRepository.save(user);
    }

    public User isDuplicatedNickName(UserDTO userDTO) {
        Optional<User> user = myPageRepository.findByNickName(userDTO.getNickName());
        return user.orElse(null);
    }

    public void selectUserNoNO(Long userId) {
        myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
//        Optional<UserNono> userNonoList = nonoRepository.findByUserIdAndIsSolved(userId, 1).orElseThrow(() -> new IllegalArgumentException("nono 찾을 수 없음 id : " + userId));

//        List<UserNonoVO> userNonnolist = userMapper.selectUserNonoByIsSolved(userId, 1);
//        model.addAttribute("title", "내가 풀고 있는 노노들");
//        model.addAttribute("isSolved", 0);
//        model.addAttribute("nonoList", userNonnolist);
    }

    public File updateProfileImgUrl(Long userId, MultipartFile imgFile) {
        User user = myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));

        byte[] content = null;
        String fileName =  imgFile.getOriginalFilename();
        try {
            content = imgFile.getBytes();
            Path directoryPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/images/profile/"+userId);

            try {
                // 디렉토리 생성
                Files.createDirectory(directoryPath);
            } catch (FileAlreadyExistsException e) {

                System.out.println("디렉토리가 이미 존재합니다");
            }

            UUID uuid = UUID.randomUUID();
            String ext = fileName.split("\\.")[1];
            fileName = uuid + "_" + user.getNickName() + "." + ext;

            File file = null;
            file = new File("/Users/jasonmilian/Downloads/nonogrammers/src/main/resources/static/images/profile/"+userId+"/"+fileName);

//            return file;
            if ( file.exists() ) {
//                return ResponseEntity.notFound().build();
                // exception 날려야하나..?
            } else {
                String fileUrl = file.getAbsolutePath().split("static")[1];
                user = user.toBuilder().profileImgUrl(fileUrl).build();
                myPageRepository.save(user);
                Path savePath = Paths.get(file.getAbsolutePath());
                imgFile.transferTo(savePath);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return null;
    }
}
