package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.dao.PostMapper;
import com.dottree.nonogrammers.domain.CommentDTO;
import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.domain.PostDTO;
import com.dottree.nonogrammers.entity.File;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.repository.CommentRepository;
import com.dottree.nonogrammers.repository.PostRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
    private final PostMapper postMapper = PostMapper.INSTANCE;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void savePost(Post post) {
        entityManager.persist(post);
    }
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    public List<PostDTO> ListAllPosts() {
//        List<PostDTO> list = postRepository.listAllPosts();
//        return list;
//    }
//    public List<PostDTO> ListAllPosts() {
//        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
//        return posts.stream()
//                .map(postMapper::postToPostDTO)
//                .collect(Collectors.toList());
//    }
    public Page<PostDTO> ListAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return posts
                .map(postMapper::postToPostDTO);
    }
    public Page<PostDTO> ListNoticePosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(1,pageable);
        return posts
                .map(postMapper::postToPostDTO);
    }
    public Page<PostDTO> ListFreePosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(2,pageable);
        return posts
                .map(postMapper::postToPostDTO);
    }
    public Page<PostDTO> ListQnAPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(3,pageable);
        return posts
                .map(postMapper::postToPostDTO);
    }
    public Page<PostDTO> ListShowNonoPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(4,pageable);
        return posts
                .map(postMapper::postToPostDTO);
    }
//    public List<PostDTO> ListFreePosts() {
//        List<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(2);
//        return posts.stream()
//                .map(postMapper::postToPostDTO)
//                .collect(Collectors.toList());
//    }
//    public List<PostDTO> ListQnAPosts() {
//        List<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(3);
//        return posts.stream()
//                .map(postMapper::postToPostDTO)
//                .collect(Collectors.toList());
//    }
//    public List<PostDTO> ListShowNonoPosts() {
//        List<Post> posts = postRepository.findByBoardTypeOrderByCreatedAtDesc(4);
//        return posts.stream()
//                .map(postMapper::postToPostDTO)
//                .collect(Collectors.toList());
//    }
    public Page<PostDTO> SearchWord(String keyword,Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContainingOrderByCreatedAtDesc(keyword,pageable);
        return posts
                .map(postMapper::postToPostDTO);
    }
//    public List<PostDTO> SearchWord(String keyword){
//        List<Post> posts = postRepository.findByTitleContainingOrderByCreatedAtDesc(keyword);
//        return posts.stream()
//                .map(postMapper::postToPostDTO)
//                .collect(Collectors.toList());
//    }
//    public List<PostDTO> ListNoticePosts() {
//        List<PostDTO> list = postRepository.listNoticePosts();
//        return list;
//    }
//
//    public List<PostDTO> ListFreePosts(){
//        List<PostDTO> list = postRepository.listFreePosts();
//        return list;
//    }
//    public List<PostDTO> ListQnAPosts(){
//        List<PostDTO> list = postRepository.listQnAPosts();
//        return list;
//    }
//    public List<PostDTO> ListShowNonoPosts(){
//        List<PostDTO> list = postRepository.listShowNonoPosts();
//        return list;
//    }
//    public List<PostDTO> SearchWord(String keyword){
//        List<PostDTO> list = postRepository.searchWord(keyword);
//        return list;
//    }
    @Transactional
    public void incrementViewCount(int postId) {
        postRepository.incrementViewCount(postId);
    }
    public PostDTO ShowDetailPost(int postId) {
        Optional<Post> postOptional = postRepository.findByPostId(postId);

        return postOptional
                .map(postMapper::postToPostDTO)
                .orElse(null); // 또는 적절한 예외 처리 로직을 추가할 수 있습니다.
    }

//    public PostDTO ShowDetailPost(int postId){
//        PostDTO postDTO = postRepository.showDetailPost(postId);
//        return postDTO;
//    }
//    public List<CommentDTO> CommentList(int postId){
//        List<CommentDTO> list = postRepository.commentList(postId);
//        return list;
//    }
    public int GetBoardType(String category){
        return postRepository.getBoardType(category);
    }
//    @Transactional
//    public int InsertPost(int boardType,int userId, String title, String content){
//        return postRepository.insertPost(boardType, userId, title, content);
//    }

    @Transactional
    public Post InsertPost(PostDTO postModel) {
        Post post = postModel.toEntity();
        return postRepository.save(post);
    }

    @Transactional
    public void DeletePost(int postId){
        postRepository.deleteByPostId(postId);
    }

    @Transactional
    public void EditPost(String title,String content,LocalDate updatedAt, int boardType,int postId,int userId){
        postRepository.editPost(title,content,updatedAt,boardType,postId,userId);
    }

//    public PostDTO FindByNickName(String nickName){
//        Optional<Post> postOptional = postRepository.findByNickName(nickName);
//        return postOptional
//                .map(postMapper::postToPostDTO)
//                .orElse(null); // 또는 적절한 예외 처리 로직을 추가할 수 있습니다.
//    }
//    @Transactional


//    @Transactional
//    public File InsertPost(FileDTO fileModel) {
//        File file = fileModel.toEntity();
//        return fileRepository.save(file);
//    }
//    @Transactional
//    public void InsertUploadImage(int postId,String filename,String fileExtension,String fileUrl){
//        postRepository.insertUploadImage(postId,filename,fileExtension,fileUrl);
//    }


////    public List<Integer> CountByPostId(int postId){
////        List<Integer> counts = postRepository.countByPostId(postId);
////    }

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
