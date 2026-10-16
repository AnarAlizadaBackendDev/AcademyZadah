package com.academy.service;

import com.academy.config.AppUserDetails;
import com.academy.domain.Course;
import com.academy.domain.CourseMonth;
import com.academy.domain.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Builds the syllabus view (months, lock state, progress) of a course for the current viewer. */
@Service
@Transactional(readOnly = true)
public class CourseViewService {

    public record LessonView(Lesson lesson, boolean accessible, boolean completed) {
    }

    public record MonthView(CourseMonth month, boolean open, LocalDate unlockDate, List<LessonView> lessons) {
    }

    public record CourseView(Course course, CourseAccess access, List<MonthView> months, int total, int completed) {

        public int percent() {
            return total == 0 ? 0 : completed * 100 / total;
        }

        public List<LessonView> allLessons() {
            return months.stream().flatMap(m -> m.lessons().stream()).toList();
        }

        /** The first open lesson the student has not finished yet. */
        public LessonView nextLesson() {
            return allLessons().stream().filter(l -> l.accessible() && !l.completed()).findFirst().orElse(null);
        }

        public MonthView nextLockedMonth() {
            return months.stream().filter(m -> !m.open() && m.unlockDate() != null).findFirst().orElse(null);
        }
    }

    private final AccessService accessService;
    private final ProgressService progressService;

    public CourseViewService(AccessService accessService, ProgressService progressService) {
        this.accessService = accessService;
        this.progressService = progressService;
    }

    public CourseView build(Course course, AppUserDetails me) {
        boolean admin = me != null && me.isAdmin();
        CourseAccess access = me == null || admin ? null : accessService.accessFor(me.getId()).get(course.getId());
        Set<Long> done = me == null ? Set.of() : progressService.completedLessonIds(me.getId());
        return build(course, access, admin, done);
    }

    public CourseView build(Course course, CourseAccess access, boolean admin, Set<Long> done) {
        LocalDate today = accessService.today();
        List<MonthView> months = new ArrayList<>();
        int total = 0;
        int completed = 0;
        for (CourseMonth month : course.getMonths()) {
            boolean open = admin || (access != null && access.isMonthOpen(month.getMonthNumber(), today));
            LocalDate unlockDate = access == null ? null : access.unlockDate(month.getMonthNumber());
            List<LessonView> lessons = new ArrayList<>();
            for (Lesson lesson : month.getLessons()) {
                if (!lesson.isPublished() && !admin) {
                    continue;
                }
                boolean isDone = done.contains(lesson.getId());
                lessons.add(new LessonView(lesson, open || lesson.isPreview(), isDone));
                total++;
                if (isDone) {
                    completed++;
                }
            }
            months.add(new MonthView(month, open, unlockDate, lessons));
        }
        return new CourseView(course, access, months, total, completed);
    }

    public Map<Long, CourseAccess> accessFor(Long userId) {
        return accessService.accessFor(userId);
    }
}
