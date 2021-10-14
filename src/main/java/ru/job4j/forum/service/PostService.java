package ru.job4j.forum.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.CommentRepository;
import ru.job4j.forum.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public List<Post> getAllPosts() {
        List<Post> allPosts = new ArrayList<>();
        postRepository.findAll().forEach(allPosts::add);
        return allPosts;
    }

    @Transactional
    public Post getPostById(int id) {
        return postRepository.findById(id).get();
    }

    @Transactional
    public void addCommentToPost(int postId, Comment comment) {
        comment.setCurrentDate();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(user);
        commentRepository.save(comment);
        postRepository.findById(postId).get().addComment(comment);
    }

    @Transactional
    public void savePost(Post post) {
        post.setCurrentDate();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setAuthor(user);
        postRepository.save(post);
    }
}
