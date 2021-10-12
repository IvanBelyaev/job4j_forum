package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.forum.service.PostService;

import java.security.Principal;

@Controller
public class IndexController {
    private final PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", "/index"})
    public String index(Principal principal, Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "index";
    }
}
