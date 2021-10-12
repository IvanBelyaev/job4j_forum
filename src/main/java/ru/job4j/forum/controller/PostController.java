package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

@Controller
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public String getPostInfo(@RequestParam("id") int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute(post);
        return "post";
    }

    @PostMapping("/addComment")
    public String addCommentToPost(@RequestParam("id") int postId, @ModelAttribute Comment comment) {
        postService.addCommentToPost(postId, comment);
        return "redirect:/post?id=" + postId;
    }

    @GetMapping("/addPost")
    public String newPostForm() {
        return "edit";
    }

    @PostMapping("/addPost")
    public String processNewPost(@ModelAttribute Post post) {
        postService.savePost(post);
        return "redirect:/post?id=" + post.getId();
    }
}
