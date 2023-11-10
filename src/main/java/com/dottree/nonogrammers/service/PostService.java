package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<PostDTO> ListFreePosts(){
        List<PostDTO> list = postRepository.listFreePosts();
        return list;
    }
    public List<PostDTO> ListQnAPosts(){
        List<PostDTO> list = postRepository.listQnAPosts();
        return list;
    }
    public List<PostDTO> ListShowNonoPosts(){
        List<PostDTO> list = postRepository.listShowNonoPosts();
        return list;
    }
    public List<PostDTO> SearchWord(String keyword){
        List<PostDTO> list = postRepository.searchWord(keyword);
        return list;
    }
    @Transactional
    public void incrementViewCount(int postId) {
        postRepository.incrementViewCount(postId);
    }
    public PostDTO ShowDetailPost(int postId){
        PostDTO postDTO = postRepository.showDetailPost(postId);
        return postDTO;
    }
    public List<CommentDTO> CommentList(int postId){
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
}
