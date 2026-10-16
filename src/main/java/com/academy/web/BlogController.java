package com.academy.web;

import com.academy.config.AppUserDetails;
import com.academy.config.WebConfig;
import com.academy.domain.BlogPost;
import com.academy.domain.PostStatus;
import com.academy.repo.BlogPostRepository;
import com.academy.service.MarkdownService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    private static final int PAGE_SIZE = 9;

    private final BlogPostRepository posts;
    private final MarkdownService markdown;

    public BlogController(BlogPostRepository posts, MarkdownService markdown) {
        this.posts = posts;
        this.markdown = markdown;
    }

    @GetMapping("/blog")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) String filter, Model model) {
        PageRequest pageable = PageRequest.of(Math.max(page, 0), PAGE_SIZE);
        Page<BlogPost> result = filter != null && WebConfig.LANGUAGES.contains(filter)
                ? posts.findByStatusAndLangOrderByPublishedAtDesc(PostStatus.PUBLISHED, filter, pageable)
                : posts.findByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED, pageable);
        model.addAttribute("posts", result);
        model.addAttribute("filter", filter);
        return "blog-list";
    }

    @GetMapping("/blog/{slug}")
    public String post(@PathVariable String slug, @AuthenticationPrincipal AppUserDetails me, Model model) {
        BlogPost post = posts.findBySlug(slug).orElseThrow(NotFound::new);
        if (!post.isPublished() && (me == null || !me.isAdmin())) {
            throw new NotFound();
        }
        model.addAttribute("post", post);
        model.addAttribute("html", markdown.toHtml(post.getContent()));
        return "blog-post";
    }
}
