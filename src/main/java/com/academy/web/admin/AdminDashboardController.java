package com.academy.web.admin;

import com.academy.domain.InquiryStatus;
import com.academy.domain.PostStatus;
import com.academy.domain.Role;
import com.academy.repo.BlogPostRepository;
import com.academy.repo.InquiryRepository;
import com.academy.repo.LessonRepository;
import com.academy.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    private final UserRepository users;
    private final InquiryRepository inquiries;
    private final BlogPostRepository posts;
    private final LessonRepository lessons;

    public AdminDashboardController(UserRepository users, InquiryRepository inquiries,
                                    BlogPostRepository posts, LessonRepository lessons) {
        this.users = users;
        this.inquiries = inquiries;
        this.posts = posts;
        this.lessons = lessons;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("activeStudents", users.countByRoleAndActiveTrue(Role.STUDENT));
        model.addAttribute("newInquiries", inquiries.countByStatus(InquiryStatus.NEW));
        model.addAttribute("publishedPosts", posts.countByStatus(PostStatus.PUBLISHED));
        model.addAttribute("lessonCount", lessons.count());
        model.addAttribute("recentInquiries", inquiries.findTop5ByOrderByCreatedAtDesc());
        return "admin/dashboard";
    }
}
