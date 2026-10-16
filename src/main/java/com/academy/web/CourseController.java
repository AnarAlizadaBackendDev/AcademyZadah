package com.academy.web;

import com.academy.config.AppUserDetails;
import com.academy.domain.Course;
import com.academy.domain.Lesson;
import com.academy.domain.LessonAttachment;
import com.academy.repo.CourseRepository;
import com.academy.repo.LessonAttachmentRepository;
import com.academy.repo.LessonRepository;
import com.academy.service.AccessService;
import com.academy.service.CourseViewService;
import com.academy.service.CourseViewService.CourseView;
import com.academy.service.CourseViewService.LessonView;
import com.academy.service.MarkdownService;
import com.academy.service.ProgressService;
import com.academy.service.StorageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class CourseController {

    private final CourseRepository courses;
    private final LessonRepository lessons;
    private final LessonAttachmentRepository attachments;
    private final AccessService accessService;
    private final CourseViewService courseViews;
    private final ProgressService progressService;
    private final MarkdownService markdown;
    private final StorageService storage;

    public CourseController(CourseRepository courses, LessonRepository lessons, LessonAttachmentRepository attachments,
                            AccessService accessService, CourseViewService courseViews, ProgressService progressService,
                            MarkdownService markdown, StorageService storage) {
        this.courses = courses;
        this.lessons = lessons;
        this.attachments = attachments;
        this.accessService = accessService;
        this.courseViews = courseViews;
        this.progressService = progressService;
        this.markdown = markdown;
        this.storage = storage;
    }

    @GetMapping("/courses/{slug}")
    public String course(@PathVariable String slug, @AuthenticationPrincipal AppUserDetails me, Model model) {
        Course course = courses.findBySlug(slug).orElseThrow(NotFound::new);
        model.addAttribute("view", courseViews.build(course, me));
        return "course";
    }

    @GetMapping("/lessons/{id}")
    public String lesson(@PathVariable Long id, @AuthenticationPrincipal AppUserDetails me, Model model) {
        Lesson lesson = lessons.findById(id).orElseThrow(NotFound::new);
        if (!lesson.isPublished() && (me == null || !me.isAdmin())) {
            throw new NotFound();
        }
        CourseView view = courseViews.build(lesson.getCourse(), me);
        model.addAttribute("lesson", lesson);
        model.addAttribute("view", view);
        if (!accessService.canView(me, lesson)) {
            if (me == null) {
                return "redirect:/login";
            }
            return "lesson-locked";
        }
        List<LessonView> all = view.allLessons();
        int index = indexOf(all, lesson.getId());
        model.addAttribute("prev", index > 0 ? all.get(index - 1) : null);
        model.addAttribute("next", index >= 0 && index < all.size() - 1 ? all.get(index + 1) : null);
        model.addAttribute("current", index >= 0 ? all.get(index) : null);
        model.addAttribute("html", markdown.toHtml(lesson.getContent()));
        model.addAttribute("embedUrl", markdown.videoEmbedUrl(lesson.getVideoUrl()));
        if (me != null && me.isAdmin() && lesson.getTeacherNotes() != null) {
            model.addAttribute("teacherHtml", markdown.toHtml(lesson.getTeacherNotes()));
        }
        return "lesson";
    }

    @PostMapping("/lessons/{id}/complete")
    public String toggleComplete(@PathVariable Long id, @AuthenticationPrincipal AppUserDetails me) {
        Lesson lesson = lessons.findById(id).orElseThrow(NotFound::new);
        if (accessService.canView(me, lesson)) {
            progressService.toggle(me.getId(), id);
        }
        return "redirect:/lessons/" + id;
    }

    @GetMapping("/lessons/{id}/files/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Long id, @PathVariable Long fileId,
                                             @AuthenticationPrincipal AppUserDetails me) {
        LessonAttachment file = attachments.findById(fileId).orElseThrow(NotFound::new);
        if (!file.getLesson().getId().equals(id) || !accessService.canView(me, file.getLesson())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        FileSystemResource resource = new FileSystemResource(storage.privateFile(file.getStoredName()));
        if (!resource.exists()) {
            throw new NotFound();
        }
        MediaType type = file.getContentType() != null
                ? MediaType.parseMediaType(file.getContentType()) : MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.ok()
                .contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(file.getOriginalName(), StandardCharsets.UTF_8).build().toString())
                .body(resource);
    }

    private static int indexOf(List<LessonView> all, Long lessonId) {
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).lesson().getId().equals(lessonId)) {
                return i;
            }
        }
        return -1;
    }
}
