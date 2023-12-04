package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.UserDTO;
import com.dottree.nonogrammers.entity.User;
import com.dottree.nonogrammers.entity.UserNono;
import com.dottree.nonogrammers.repository.MyPageRepository;
import com.dottree.nonogrammers.repository.UserNonoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final UserNonoRepository userNonoRepository;

    public MyPageService(MyPageRepository myPageRepository, UserNonoRepository userNonoRepository) {
        this.myPageRepository = myPageRepository;
        this.userNonoRepository = userNonoRepository;
    }

    public UserDTO user(Integer userId) {
        return myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId)).toDto();
    }

    /**
     * 유저의 정보를 불러옵니다.
     * @param nickName
     */
    public UserDTO getUser(String nickName) {
        log.info(myPageRepository.findByNickName(nickName).get().getProfileImgUrl());
        return myPageRepository.findByNickName(nickName).orElseThrow(()
                -> new IllegalArgumentException("user 찾을 수 없음 nickName : " + nickName)).toDto();
    }

    @Transactional
    public void updateNickName(Integer userId, UserDTO userDTO) {
        // DB에서 조회한다.
        User user = myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
        // 조회한 유저의 닉네임 수정 - dirty checking
        user.changeNickName(userDTO.getNickName());
    }

    @Transactional
    public void updateStatusCode(Integer userId) {
        User user = myPageRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
        // dirty checking - don't call save().
        user.changeStatusCode(0);
    }

    public User isDuplicatedNickName(String nickName) {
        Optional<User> user = myPageRepository.findByNickName(nickName);
        return user.orElse(null);
    }

    public User selectUserNoNO(Integer userId) {
        return myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
    }

    @Transactional
    public UserDTO updateProfileImgUrl(Integer userId, MultipartFile imgFile) {
        User user = myPageRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user 찾을 수 없음 id : " +  userId));
        String fileName =  imgFile.getOriginalFilename();

        try {
            Path directoryPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/images/profile/"+userId);

            try {
                // 디렉토리 생성
                Files.createDirectory(directoryPath);
            } catch (FileAlreadyExistsException e) {
                log.info("디렉토리가 이미 존재합니다");
            }

            UUID uuid = UUID.randomUUID();
            String ext = fileName.split("\\.")[1];
            fileName = uuid + "_" + user.getNickName() + "." + ext;
            //System.out.println(directoryPath);
            File file;
            file = new File(directoryPath + "/" + fileName);
            if ( file.exists() ) {
                return null;
            } else {
                String fileUrl = file.getAbsolutePath().split("static")[1];
                log.info("바뀌기 전 : " + user.getProfileImgUrl());
                user.changeProfileImgUrl(fileUrl);
                log.info("바뀐 후 : " + user.getProfileImgUrl());

                Path savePath = Paths.get(file.getAbsolutePath());
                imgFile.transferTo(savePath);

                return user.toDto();
            }
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public UserNono getUserNonoDetail(Integer userId, Integer nonoId, Integer isSolved) {
        return userNonoRepository.findByUser_UserIdAndNono_NonoIdAndIsSolved(userId, nonoId, isSolved).orElseThrow(()
                -> new IllegalArgumentException("userNono 찾을 수 없음 userId : " + userId + ", nonoId : " + nonoId));
    };

    public List<UserNono> getUserNoNoList(Integer userId, Integer isSolved) {
        return userNonoRepository.findByUser_UserIdAndIsSolved(userId, isSolved).orElseThrow(()
                -> new IllegalArgumentException("userNono 찾을 수 없음 userId : " + userId));
    }
}
