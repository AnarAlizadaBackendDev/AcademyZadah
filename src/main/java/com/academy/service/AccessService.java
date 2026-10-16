package com.academy.service;

import com.academy.config.AppUserDetails;
import com.academy.domain.Course;
import com.academy.domain.Enrollment;
import com.academy.domain.Lesson;
import com.academy.repo.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/** The single place that decides who can open which lesson. */
@Service
@Transactional(readOnly = true)
public class AccessService {

    private final EnrollmentRepository enrollments;
    private final Clock clock;

    public AccessService(EnrollmentRepository enrollments, Clock clock) {
        this.enrollments = enrollments;
        this.clock = clock;
    }

    public LocalDate today() {
        return LocalDate.now(clock);
    }

    /** Course id -> access, for every course the user can currently study (ordered by course). */
    public Map<Long, CourseAccess> accessFor(Long userId) {
        Map<Long, CourseAccess> result = new LinkedHashMap<>();
        LocalDate today = today();
        for (Enrollment e : enrollments.findByUserId(userId)) {
            if (!e.isValidOn(today)) {
                continue;
            }
            Course course = e.getCourse();
            result.merge(course.getId(), new CourseAccess(course, e.getStartDate(), false), CourseAccess::merge);
            Course included = course.getIncludesCourse();
            if (included != null) {
                result.merge(included.getId(), new CourseAccess(included, e.getStartDate(), true), CourseAccess::merge);
            }
        }
        Map<Long, CourseAccess> sorted = new LinkedHashMap<>();
        result.values().stream()
                .sorted(Comparator.comparingInt(a -> a.course().getSortOrder()))
                .forEach(a -> sorted.put(a.course().getId(), a));
        return sorted;
    }

    /** @param me the logged-in user, or null for a visitor */
    public boolean canView(AppUserDetails me, Lesson lesson) {
        if (me != null && me.isAdmin()) {
            return true;
        }
        if (!lesson.isPublished()) {
            return false;
        }
        if (lesson.isPreview()) {
            return true;
        }
        if (me == null) {
            return false;
        }
        CourseAccess access = accessFor(me.getId()).get(lesson.getCourse().getId());
        return access != null && access.isMonthOpen(lesson.getMonth().getMonthNumber(), today());
    }
}
