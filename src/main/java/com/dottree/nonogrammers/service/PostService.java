package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.repository.PostRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> ListAllPosts() {
        List<PostDTO> list = postRepository.listAllPosts();
        return list;
    }

    public List<PostDTO> ListNoticePosts() {
        List<PostDTO> list = postRepository.listNoticePosts();
        return list;
    }

    public List<PostDTO> ListFreePosts() {
        List<PostDTO> list = postRepository.listFreePosts();
        return list;
    }

    public List<PostDTO> ListQnAPosts() {
        List<PostDTO> list = postRepository.listQnAPosts();
        return list;
    }

    public List<PostDTO> ListShowNonoPosts() {
        List<PostDTO> list = postRepository.listShowNonoPosts();
        return list;
    }

    public List<PostDTO> SearchWord(String keyword) {
        List<PostDTO> list = postRepository.searchWord(keyword);
        return list;
    }

    @Transactional
    public void incrementViewCount(int postId) {
        postRepository.incrementViewCount(postId);
    }

    public PostDTO ShowDetailPost(int postId) {
        PostDTO postDTO = postRepository.showDetailPost(postId);
        return postDTO;
    }

    public List<CommentDTO> CommentList(int postId) {
        List<CommentDTO> list = postRepository.commentList(postId);
        return list;
    }
//    public int GetBoardType(String category){
//        int boardNum=postRepository.getBoardType(category);
//        return boardNum;
//    }
//    @Transactional
//    public boolean InsertPost(int boardType,int userId, String title, String content){
//        boolean isInsertPost = postRepository.insertPost(boardType, userId, title, content);
//        return isInsertPost;
//    }
//    @Transactional
//    public boolean InsertUploadImage(int postId,String filename,String fileExtension,String fileUrl){
//        boolean isUploadImage = postRepository.insertUploadImage(postId,filename,fileExtension,fileUrl);
//        return isUploadImage;
//    }
//    public List<Integer> CountByPostId(int postId){
//        List<Integer> counts = postRepository.countByPostId(postId);
//    }

    /**
     * 유저의 게시물을 모두 조회합니다.
     * @param userId
     * @return List<PostDTO>
     */
    public List<PostDTO> getUserPostList(Integer userId) {
        List<PostDTO> postDTOList = new ArrayList<>();

        postRepository.findAllByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 게시물을 찾을 수 없음 userId" + userId))
                .forEach(post -> postDTOList.add(post.toDto()));

        return postDTOList;
    }

    /**
     * 유저의 게시물을 수정합니다.
     * @param title
     * @param content
     * @param postId
     * @param userId
     */
    @Transactional
    public void changeUserPost(String title, String content, Integer postId, Integer userId) {
        Post post = postRepository.findByPostIdAndUserId(postId, userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없음 postId : " + postId + ", userId : " + userId));
        post.changeTitleAndContentAndUpdatedAt(title, content, LocalDate.now());
    }
}
