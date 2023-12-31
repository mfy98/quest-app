package com.filxdev.questapp.services;

import com.filxdev.questapp.entities.Like;
import com.filxdev.questapp.entities.Post;
import com.filxdev.questapp.entities.User;
import com.filxdev.questapp.repos.LikeRepository;
import com.filxdev.questapp.repos.PostRepository;
import com.filxdev.questapp.requests.PostCreateRequest;
import com.filxdev.questapp.requests.PostUpdateRequest;
import com.filxdev.questapp.responses.LikeResponse;
import com.filxdev.questapp.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;


    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }
    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;

        if(userId.isPresent()){
            list = postRepository.findByUserId(userId.get());
        }
        list = postRepository.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(null,Optional.of(p.getId()));
            return new PostResponse(p,likes);}).collect(Collectors.toList());

    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUserById(newPostRequest.getUserId());
        if(user == null) {
            return null;
        }
        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setText(updatePost.getText());
            toUpdate.setTitle(updatePost.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
