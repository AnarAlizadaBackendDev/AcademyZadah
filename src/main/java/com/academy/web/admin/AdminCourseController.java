package com.academy.web.admin;

import com.academy.domain.CourseMonth;
import com.academy.domain.Lesson;
import com.academy.domain.LessonAttachment;
import com.academy.repo.CourseMonthRepository;
import com.academy.repo.CourseRepository;
import com.academy.repo.LessonAttachmentRepository;
import com.academy.repo.LessonRepository;
import com.academy.service.StorageService;
import com.academy.web.NotFound;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {

    private final CourseRepository courses;
    private final CourseMonthRepository months;
    private final LessonRepository lessons;
    private final LessonAttachmentRepository attachments;
    private final StorageService storage;

    public AdminCourseController(CourseRepository courses, CourseMonthRepository months, LessonRepository lessons,
                                 LessonAttachmentRepository attachments, StorageService storage) {
        this.courses = courses;
        this.months = months;
        this.lessons = lessons;
        this.attachments = attachments;
        this.storage = storage;
    }

    @GetMapping("/courses")
    public String list(Model model) {
        model.addAttribute("courses", courses.findAllByOrderBySortOrder());
        return "admin/courses";
    }

    @GetMapping("/courses/{id}")
    public String course(@PathVariable Long id, Model model) {
        model.addAttribute("course", courses.findById(id).orElseThrow(NotFound::new));
        return "admin/course";
    }

    @PostMapping("/months/{id}")
    @Transactional
    public String updateMonth(@PathVariable Long id, @RequestParam String title,
                              @RequestParam(required = false) String description, RedirectAttributes redirect) {
        CourseMonth month = months.findById(id).orElseThrow(NotFound::new);
        month.setTitle(title.trim());
        month.setDescription(description);
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/courses/" + month.getCourse().getId() + "#month-" + month.getMonthNumber();
    }

    @GetMapping("/months/{id}/lessons/new")
    public String newLesson(@PathVariable Long id, Model model) {
        CourseMonth month = months.findById(id).orElseThrow(NotFound::new);
        Lesson lesson = new Lesson();
        lesson.setMonth(month);
        lesson.setSortOrder((month.getLessons().size() + 1) * 10);
        model.addAttribute("lesson", lesson);
        return "admin/lesson-form";
    }

    @PostMapping("/months/{id}/lessons")
    @Transactional
    public String createLesson(@PathVariable Long id, LessonForm form, RedirectAttributes redirect) {
        Lesson lesson = new Lesson();
        lesson.setMonth(months.findById(id).orElseThrow(NotFound::new));
        form.applyTo(lesson);
        lessons.save(lesson);
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/lessons/" + lesson.getId() + "/edit";
    }

    @GetMapping("/lessons/{id}/edit")
    public String editLesson(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", lessons.findById(id).orElseThrow(NotFound::new));
        return "admin/lesson-form";
    }

    @PostMapping("/lessons/{id}")
    @Transactional
    public String updateLesson(@PathVariable Long id, LessonForm form, RedirectAttributes redirect) {
        Lesson lesson = lessons.findById(id).orElseThrow(NotFound::new);
        form.applyTo(lesson);
        lesson.setUpdatedAt(LocalDateTime.now());
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/lessons/" + id + "/edit";
    }

    @PostMapping("/lessons/{id}/delete")
    @Transactional
    public String deleteLesson(@PathVariable Long id, RedirectAttributes redirect) {
        Lesson lesson = lessons.findById(id).orElseThrow(NotFound::new);
        Long courseId = lesson.getCourse().getId();
        lesson.getAttachments().forEach(a -> storage.deletePrivate(a.getStoredName()));
        lesson.getMonth().getLessons().remove(lesson);
        redirect.addFlashAttribute("flash", "flash.deleted");
        return "redirect:/admin/courses/" + courseId;
    }

    @PostMapping("/lessons/{id}/attachments")
    @Transactional
    public String uploadAttachments(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files,
                                    RedirectAttributes redirect) {
        Lesson lesson = lessons.findById(id).orElseThrow(NotFound::new);
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String stored = storage.storePrivate(file);
            lesson.getAttachments().add(new LessonAttachment(lesson, file.getOriginalFilename(), stored,
                    file.getContentType(), file.getSize()));
        }
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/lessons/" + id + "/edit#files";
    }

    @PostMapping("/attachments/{id}/delete")
    @Transactional
    public String deleteAttachment(@PathVariable Long id, RedirectAttributes redirect) {
        LessonAttachment attachment = attachments.findById(id).orElseThrow(NotFound::new);
        Lesson lesson = attachment.getLesson();
        storage.deletePrivate(attachment.getStoredName());
        lesson.getAttachments().remove(attachment);
        redirect.addFlashAttribute("flash", "flash.deleted");
        return "redirect:/admin/lessons/" + lesson.getId() + "/edit#files";
    }

    public static class LessonForm {
        private String title;
        private int sortOrder;
        private String content;
        private String videoUrl;
        private String teacherNotes;
        private boolean preview;
        private boolean published;

        void applyTo(Lesson lesson) {
            lesson.setTitle(title.trim());
            lesson.setSortOrder(sortOrder);
            lesson.setContent(content);
            lesson.setVideoUrl(videoUrl == null || videoUrl.isBlank() ? null : videoUrl.trim());
            lesson.setTeacherNotes(teacherNotes);
            lesson.setPreview(preview);
            lesson.setPublished(published);
        }

        public void setTitle(String title) { this.title = title; }
        public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
        public void setContent(String content) { this.content = content; }
        public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
        public void setTeacherNotes(String teacherNotes) { this.teacherNotes = teacherNotes; }
        public void setPreview(boolean preview) { this.preview = preview; }
        public void setPublished(boolean published) { this.published = published; }
    }
}
