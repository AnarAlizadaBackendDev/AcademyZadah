package com.academy.web;

import com.academy.domain.PostStatus;
import com.academy.repo.BlogPostRepository;
import com.academy.repo.CourseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CourseRepository courses;
    private final BlogPostRepository posts;

    public HomeController(CourseRepository courses, BlogPostRepository posts) {
        this.courses = courses;
        this.posts = posts;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("courses", courses.findAllByOrderBySortOrder());
        model.addAttribute("posts", posts.findByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED, PageRequest.of(0, 3)));
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return model.getAttribute("me") != null ? "redirect:/dashboard" : "login";
    }
}
