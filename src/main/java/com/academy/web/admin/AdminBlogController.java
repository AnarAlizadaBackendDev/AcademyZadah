package com.academy.web.admin;

import com.academy.domain.BlogPost;
import com.academy.domain.PostStatus;
import com.academy.repo.BlogPostRepository;
import com.academy.service.BlogService;
import com.academy.web.NotFound;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {

    private final BlogPostRepository posts;
    private final BlogService blogService;

    public AdminBlogController(BlogPostRepository posts, BlogService blogService) {
        this.posts = posts;
        this.blogService = blogService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", posts.findAllByOrderByCreatedAtDesc());
        return "admin/blog";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new BlogPost());
        return "admin/blog-form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("post", posts.findById(id).orElseThrow(NotFound::new));
        return "admin/blog-form";
    }

    @PostMapping({"", "/{id}"})
    public String save(@PathVariable(required = false) Long id,
                       @RequestParam String title, @RequestParam(required = false) String slug,
                       @RequestParam(required = false) String summary, @RequestParam(required = false) String coverImage,
                       @RequestParam(required = false) String content, @RequestParam String lang,
                       @RequestParam PostStatus status, RedirectAttributes redirect) {
        BlogPost post = id == null ? new BlogPost() : posts.findById(id).orElseThrow(NotFound::new);
        post.setTitle(title.trim());
        post.setSummary(summary);
        post.setCoverImage(coverImage == null || coverImage.isBlank() ? null : coverImage);
        post.setContent(content);
        post.setLang(lang);
        post.setStatus(status);
        post = blogService.save(post, slug);
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/blog/" + post.getId() + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        posts.deleteById(id);
        redirect.addFlashAttribute("flash", "flash.deleted");
        return "redirect:/admin/blog";
    }
}
