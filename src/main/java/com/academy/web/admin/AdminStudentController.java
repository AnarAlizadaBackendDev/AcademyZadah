package com.academy.web.admin;

import com.academy.config.AppProperties;
import com.academy.domain.Role;
import com.academy.domain.User;
import com.academy.repo.CourseRepository;
import com.academy.repo.UserRepository;
import com.academy.service.CourseAccess;
import com.academy.service.CourseViewService;
import com.academy.service.ProgressService;
import com.academy.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminStudentController {

    private final UserRepository users;
    private final CourseRepository courses;
    private final StudentService students;
    private final CourseViewService courseViews;
    private final ProgressService progressService;
    private final AppProperties props;

    public AdminStudentController(UserRepository users, CourseRepository courses, StudentService students,
                                  CourseViewService courseViews, ProgressService progressService, AppProperties props) {
        this.users = users;
        this.courses = courses;
        this.students = students;
        this.courseViews = courseViews;
        this.progressService = progressService;
        this.props = props;
    }

    @GetMapping("/students")
    public String list(@RequestParam(required = false) String q, Model model) {
        List<User> list = users.findAllByRoleOrderByCreatedAtDesc(Role.STUDENT);
        if (q != null && !q.isBlank()) {
            String needle = q.toLowerCase();
            list = list.stream().filter(u -> u.getFullName().toLowerCase().contains(needle)
                    || u.getEmail().contains(needle)).toList();
        }
        model.addAttribute("students", list);
        model.addAttribute("q", q);
        return "admin/students";
    }

    @GetMapping("/students/new")
    public String newForm(Model model) {
        model.addAttribute("courses", courses.findAllByOrderBySortOrder());
        model.addAttribute("today", LocalDate.now());
        return "admin/student-new";
    }

    @PostMapping("/students")
    public String create(@RequestParam String fullName, @RequestParam String email,
                         @RequestParam(required = false) String phone, @RequestParam(required = false) String notes,
                         @RequestParam(required = false) Long courseId,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         Model model, RedirectAttributes redirect) {
        try {
            StudentService.Created created = students.create(fullName, email, phone, notes, courseId, startDate);
            redirect.addFlashAttribute("credentials", credentials(created.user().getEmail(), created.password()));
            return "redirect:/admin/students/" + created.user().getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("courses", courses.findAllByOrderBySortOrder());
            model.addAttribute("today", LocalDate.now());
            return "admin/student-new";
        }
    }

    @GetMapping("/students/{id}")
    public String detail(@PathVariable Long id, Model model) {
        User student = students.get(id);
        Set<Long> done = progressService.completedLessonIds(id);
        model.addAttribute("student", student);
        model.addAttribute("courses", courses.findAllByOrderBySortOrder());
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("views", courseViews.accessFor(id).values().stream()
                .map((CourseAccess a) -> courseViews.build(a.course(), a, false, done)).toList());
        return "admin/student-detail";
    }

    @PostMapping("/students/{id}")
    public String update(@PathVariable Long id, @RequestParam String fullName, @RequestParam String email,
                         @RequestParam(required = false) String phone, @RequestParam(required = false) String notes,
                         RedirectAttributes redirect) {
        try {
            students.update(id, fullName, email, phone, notes);
            redirect.addFlashAttribute("flash", "flash.saved");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students/" + id;
    }

    @PostMapping("/students/{id}/reset-password")
    public String resetPassword(@PathVariable Long id, RedirectAttributes redirect) {
        String password = students.resetPassword(id);
        redirect.addFlashAttribute("credentials", credentials(students.get(id).getEmail(), password));
        return "redirect:/admin/students/" + id;
    }

    @PostMapping("/students/{id}/toggle-active")
    public String toggleActive(@PathVariable Long id, RedirectAttributes redirect) {
        students.toggleActive(id);
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/students/" + id;
    }

    @PostMapping("/students/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        students.delete(id);
        redirect.addFlashAttribute("flash", "flash.deleted");
        return "redirect:/admin/students";
    }

    @PostMapping("/students/{id}/enrollments")
    public String enroll(@PathVariable Long id, @RequestParam Long courseId,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         RedirectAttributes redirect) {
        try {
            students.enroll(id, courseId, startDate, endDate);
            redirect.addFlashAttribute("flash", "flash.saved");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students/" + id;
    }

    @PostMapping("/enrollments/{id}")
    public String updateEnrollment(@PathVariable Long id,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam(defaultValue = "false") boolean active,
                                   RedirectAttributes redirect) {
        Long userId = students.updateEnrollment(id, startDate, endDate, active);
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/students/" + userId;
    }

    @PostMapping("/enrollments/{id}/delete")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirect) {
        Long userId = students.deleteEnrollment(id);
        redirect.addFlashAttribute("flash", "flash.deleted");
        return "redirect:/admin/students/" + userId;
    }

    /** Ready-to-send text with the login details, shown once after create/reset. */
    private String credentials(String email, String password) {
        return props.baseUrl() + "/login\nLogin: " + email + "\nPassword: " + password;
    }
}
