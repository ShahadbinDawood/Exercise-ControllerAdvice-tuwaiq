package com.example.lab11.Service;

import com.example.lab11.Api.ApiException;
import com.example.lab11.Model.Post;
import com.example.lab11.Model.User;
import com.example.lab11.Repository.CategoryRepository;
import com.example.lab11.Repository.PostRepository;
import com.example.lab11.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public void addPost(Post post) {
        if (post == null) {
            throw new ApiException("Post not found");
        }
        if (categoryRepository.findCategoryByCategoryId(post.getCategoryId()) == null) {
            throw new ApiException("category not found");
        }
        if (userRepository.findUserByUserId(post.getUserId()) == null) {
            throw new ApiException("user not found");

        }
        postRepository.save(post);
    }

    public void updatePost(Integer id, Post post) {
        Post oldPost = postRepository.findPostByPostId(id);
        if (oldPost == null) {
            throw new ApiException("Post not found");
        }
        if (categoryRepository.findCategoryByCategoryId(post.getCategoryId()) == null) {
            throw new ApiException("category not found");
        }
        if (userRepository.findUserByUserId(post.getUserId()) == null) {
            throw new ApiException("user not found");
        }
        oldPost.setCategoryId(post.getCategoryId());
        oldPost.setTitle(post.getTitle());
        oldPost.setContent(post.getContent());
        oldPost.setUserId(post.getUserId());
        oldPost.setPublishDate(post.getPublishDate());
        postRepository.save(oldPost);

    }

    public void deletePost(Integer id) {
        Post post = postRepository.findPostByPostId(id);
        if (post == null) {
            throw new ApiException("post not found");
        }
        postRepository.delete(post);
    }

    public List<Post> findPostByUserId(Integer id) {
        List<Post> posts = postRepository.findPostByUserId(id);
        if (posts.isEmpty()) {
            throw new ApiException("post not found");
        }
        return posts;
    }

    public List<Post> findPostByCategoryId(Integer id) {
        List<Post> posts = postRepository.findPostByCategoryId(id);
        if (posts.isEmpty()) {
            throw new ApiException("post not found");
        }
        return posts;
    }

    public Post findPostByTitle(String title) {
        Post post = postRepository.findPostByTitle(title);
        if (post == null) {
            throw new ApiException("post not found");
        }
        return post;
    }

    public List<Post> findPostByCategoryIdAndUserId(Integer categoryId, Integer userId) {
        if (categoryRepository.findCategoryByCategoryId(categoryId) == null) {
            throw new ApiException("category not found");
        }
        if (userRepository.findUserByUserId(userId) == null) {
            throw new ApiException("user not found");
        }
        List<Post> posts = postRepository.findPostsByCategoryIdAndUserId(categoryId, userId);
        if (posts.isEmpty()) {
            throw new ApiException("post not found");
        }
        return posts;
    }
    public List<Post> findPostAfterDate(Date date) {
        List<Post> posts = postRepository.findPostByPublishDateAfter(date);
        if (posts.isEmpty()) {
            throw new ApiException("No posts found after this date");
        }
        return posts;
    }
    public  int countPostByUserId(Integer id){
        if (userRepository.findUserByUserId(id) == null) {
            throw new ApiException("Post not found");
        }
        return postRepository.countPostByUserId(id);
    }
}

