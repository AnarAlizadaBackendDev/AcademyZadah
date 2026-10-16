package com.academy.web;

import com.academy.config.AppUserDetails;
import com.academy.service.CourseAccess;
import com.academy.service.CourseViewService;
import com.academy.service.ProgressService;
import com.academy.service.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class StudentController {

    private final CourseViewService courseViews;
    private final ProgressService progressService;
    private final StudentService studentService;

    public StudentController(CourseViewService courseViews, ProgressService progressService, StudentService studentService) {
        this.courseViews = courseViews;
        this.progressService = progressService;
        this.studentService = studentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal AppUserDetails me, Model model) {
        if (me.isAdmin()) {
            return "redirect:/admin";
        }
        Set<Long> done = progressService.completedLessonIds(me.getId());
        model.addAttribute("views", courseViews.accessFor(me.getId()).values().stream()
                .map((CourseAccess access) -> courseViews.build(access.course(), access, false, done))
                .toList());
        return "dashboard";
    }

    @GetMapping("/account/password")
    public String passwordForm() {
        return "account-password";
    }

    @PostMapping("/account/password")
    public String changePassword(@AuthenticationPrincipal AppUserDetails me,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model) {
        String error = null;
        if (newPassword.length() < 8) {
            error = "account.password.tooShort";
        } else if (!newPassword.equals(confirmPassword)) {
            error = "account.password.mismatch";
        } else if (!studentService.changeOwnPassword(me.getId(), currentPassword, newPassword)) {
            error = "account.password.wrongCurrent";
        }
        if (error != null) {
            model.addAttribute("error", error);
            return "account-password";
        }
        return "redirect:/dashboard?passwordChanged";
    }
}
