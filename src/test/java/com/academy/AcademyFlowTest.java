package com.academy;

import com.academy.config.AppUserDetails;
import com.academy.domain.Course;
import com.academy.domain.Enrollment;
import com.academy.domain.Lesson;
import com.academy.domain.User;
import com.academy.repo.CourseRepository;
import com.academy.repo.EnrollmentRepository;
import com.academy.repo.InquiryRepository;
import com.academy.repo.LessonRepository;
import com.academy.repo.UserRepository;
import com.academy.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AcademyFlowTest {

    @Autowired MockMvc mvc;
    @Autowired UserRepository users;
    @Autowired CourseRepository courses;
    @Autowired LessonRepository lessons;
    @Autowired EnrollmentRepository enrollments;
    @Autowired InquiryRepository inquiries;
    @Autowired StudentService studentService;
    @Autowired TransactionTemplate tx;

    AppUserDetails admin;
    Course essentials;
    Course advanced;

    @BeforeEach
    void setUp() {
        admin = new AppUserDetails(users.findByEmailIgnoreCase("admin@test.dev").orElseThrow());
        essentials = courses.findBySlug("java-essentials").orElseThrow();
        advanced = courses.findBySlug("java-advanced").orElseThrow();
    }

    @Test
    void publicPagesRenderInAllLanguages() throws Exception {
        for (String lang : new String[]{"az", "ru", "en"}) {
            mvc.perform(get("/").param("lang", lang)).andExpect(status().isOk())
                    .andExpect(content().string(containsString("Java Essentials")));
        }
        mvc.perform(get("/courses/java-advanced")).andExpect(status().isOk());
        mvc.perform(get("/blog")).andExpect(status().isOk());
        mvc.perform(get("/contact")).andExpect(status().isOk());
        mvc.perform(get("/login")).andExpect(status().isOk());
        mvc.perform(get("/sitemap.xml")).andExpect(status().isOk());
        mvc.perform(get("/courses/nope")).andExpect(status().isNotFound());
    }

    @Test
    void adminPagesRequireAdmin() throws Exception {
        mvc.perform(get("/admin")).andExpect(status().is3xxRedirection());
        Created student = createStudent(essentials);
        mvc.perform(get("/admin").with(user(student.details()))).andExpect(status().isForbidden());
        for (String page : new String[]{"/admin", "/admin/students", "/admin/students/new", "/admin/courses",
                "/admin/courses/" + essentials.getId(), "/admin/blog", "/admin/blog/new", "/admin/inquiries",
                "/admin/students/" + student.user().getId()}) {
            mvc.perform(get(page).with(user(admin))).andExpect(status().isOk());
        }
    }

    @Test
    void previewLessonsArePublicAndMonthsUnlockByEnrollment() throws Exception {
        Long preview = createLesson(essentials, 1, true);
        Long month1 = createLesson(essentials, 1, false);
        Long month2 = createLesson(essentials, 2, false);
        Long advancedMonth1 = createLesson(advanced, 1, false);

        // visitor
        mvc.perform(get("/lessons/" + preview)).andExpect(status().isOk()).andExpect(view().name("lesson"));
        mvc.perform(get("/lessons/" + month1)).andExpect(redirectedUrl("/login"));

        // Essentials student who started today: month 1 open, month 2 locked, Advanced not accessible
        Created student = createStudent(essentials);
        changePassword(student);
        mvc.perform(get("/lessons/" + month1).with(user(student.details()))).andExpect(view().name("lesson"));
        mvc.perform(get("/lessons/" + month2).with(user(student.details()))).andExpect(view().name("lesson-locked"));
        mvc.perform(get("/lessons/" + advancedMonth1).with(user(student.details()))).andExpect(view().name("lesson-locked"));

        // Advanced student: all Essentials months open, Advanced month 1 open
        Created adv = createStudent(advanced);
        changePassword(adv);
        mvc.perform(get("/lessons/" + month2).with(user(adv.details()))).andExpect(view().name("lesson"));
        mvc.perform(get("/lessons/" + advancedMonth1).with(user(adv.details()))).andExpect(view().name("lesson"));
        mvc.perform(get("/dashboard").with(user(adv.details()))).andExpect(status().isOk())
                .andExpect(content().string(containsString("Java Advanced")))
                .andExpect(content().string(containsString("Java Essentials")));

        // An ended enrollment loses access
        tx.executeWithoutResult(s -> enrollments.findByUserId(student.user().getId())
                .forEach(e -> e.setEndDate(LocalDate.now().minusDays(1))));
        mvc.perform(get("/lessons/" + month1).with(user(student.details()))).andExpect(view().name("lesson-locked"));

        // Progress
        mvc.perform(post("/lessons/" + advancedMonth1 + "/complete").with(csrf()).with(user(adv.details())))
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/courses/java-advanced").with(user(adv.details())))
                .andExpect(content().string(containsString("✓")));
    }

    @Test
    void newStudentMustChangePasswordAndCanLogIn() throws Exception {
        String email = "s-" + UUID.randomUUID() + "@test.dev";
        String location = mvc.perform(post("/admin/students").with(csrf()).with(user(admin))
                        .param("fullName", "Test Student").param("email", email)
                        .param("courseId", essentials.getId().toString()).param("startDate", LocalDate.now().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("credentials"))
                .andReturn().getResponse().getRedirectedUrl();
        assertThat(location).startsWith("/admin/students/");

        User created = users.findByEmailIgnoreCase(email).orElseThrow();
        assertThat(created.isMustChangePassword()).isTrue();
        assertThat(enrollments.findByUserId(created.getId())).hasSize(1);

        String password = studentService.resetPassword(created.getId());
        mvc.perform(post("/login").with(csrf()).param("username", email).param("password", password))
                .andExpect(redirectedUrl("/dashboard"));
        mvc.perform(post("/login").with(csrf()).param("username", email).param("password", "wrong"))
                .andExpect(redirectedUrl("/login?error"));

        AppUserDetails me = new AppUserDetails(created);
        mvc.perform(get("/dashboard").with(user(me))).andExpect(redirectedUrl("/account/password"));
        mvc.perform(post("/account/password").with(csrf()).with(user(me))
                        .param("currentPassword", password).param("newPassword", "short").param("confirmPassword", "short"))
                .andExpect(model().attribute("error", "account.password.tooShort"));
        mvc.perform(post("/account/password").with(csrf()).with(user(me))
                        .param("currentPassword", password).param("newPassword", "newSecret123").param("confirmPassword", "newSecret123"))
                .andExpect(redirectedUrl("/dashboard?passwordChanged"));
        mvc.perform(get("/dashboard").with(user(me))).andExpect(status().isOk());

        // deactivated users are logged out
        mvc.perform(post("/admin/students/" + created.getId() + "/toggle-active").with(csrf()).with(user(admin)));
        mvc.perform(get("/dashboard").with(user(me))).andExpect(redirectedUrl("/login?disabled"));
    }

    @Test
    void blogPostsDraftAndPublish() throws Exception {
        String title = "Virtual threads " + UUID.randomUUID().toString().substring(0, 6);
        mvc.perform(post("/admin/blog").with(csrf()).with(user(admin))
                        .param("title", title).param("lang", "en").param("status", "DRAFT")
                        .param("content", "# Hello\n\n```java\nvar x = 1;\n```"))
                .andExpect(status().is3xxRedirection());
        String slug = com.academy.service.Slugs.of(title);
        mvc.perform(get("/blog/" + slug)).andExpect(status().isNotFound());
        mvc.perform(get("/blog/" + slug).with(user(admin))).andExpect(status().isOk());

        String editUrl = mvc.perform(post("/admin/blog").with(csrf()).with(user(admin))
                        .param("title", title).param("lang", "en").param("status", "PUBLISHED").param("content", "Body"))
                .andReturn().getResponse().getRedirectedUrl();
        assertThat(editUrl).matches("/admin/blog/\\d+/edit");
        mvc.perform(get("/blog/" + slug + "-2")).andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));
    }

    @Test
    void imageUploadAndContactForm() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "pic.png", "image/png", new byte[]{1, 2, 3});
        mvc.perform(multipart("/admin/uploads/image").file(image).with(csrf()).with(user(admin)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.url").value(org.hamcrest.Matchers.startsWith("/media/")));
        MockMultipartFile exe = new MockMultipartFile("image", "evil.exe", "application/octet-stream", new byte[]{1});
        mvc.perform(multipart("/admin/uploads/image").file(exe).with(csrf()).with(user(admin)))
                .andExpect(status().isBadRequest());

        long before = inquiries.count();
        mvc.perform(post("/contact").with(csrf()).param("name", "Ali").param("email", "ali@test.dev").param("course", "java-essentials"))
                .andExpect(redirectedUrl("/contact?sent"));
        mvc.perform(post("/contact").with(csrf()).param("name", "").param("email", "bad"))
                .andExpect(view().name("contact"));
        assertThat(inquiries.count()).isEqualTo(before + 1);
    }

    record Created(User user, AppUserDetails details) {
    }

    private Created createStudent(Course course) {
        StudentService.Created c = studentService.create("Student", "s-" + UUID.randomUUID() + "@test.dev",
                null, null, course.getId(), LocalDate.now());
        return new Created(c.user(), new AppUserDetails(c.user()));
    }

    private void changePassword(Created student) {
        tx.executeWithoutResult(s -> users.findById(student.user().getId()).orElseThrow().setMustChangePassword(false));
    }

    private Long createLesson(Course course, int monthNumber, boolean preview) {
        return tx.execute(s -> {
            Course c = courses.findById(course.getId()).orElseThrow();
            Lesson lesson = new Lesson();
            lesson.setMonth(c.getMonths().get(monthNumber - 1));
            lesson.setTitle("Lesson " + UUID.randomUUID());
            lesson.setContent("Some **content**");
            lesson.setPreview(preview);
            return lessons.save(lesson).getId();
        });
    }
}
