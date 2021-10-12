package ru.job4j.forum.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.ForumRepository;

import java.util.List;

@Service
public class PostService {

    private ForumRepository forumRepository;

    public PostService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public List<Post> getAllPosts() {
        return forumRepository.getAllPosts();
    }

    public Post getPostById(int id) {
        return forumRepository.getPostById(id);
    }

    public void addCommentToPost(int postId, Comment comment) {
        comment.setCurrentDate();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(user);
        forumRepository.addCommentToPost(postId, comment);
    }

    public void savePost(Post post) {
        post.setCurrentDate();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setAuthor(user);
        forumRepository.addPost(post);
    }
}
