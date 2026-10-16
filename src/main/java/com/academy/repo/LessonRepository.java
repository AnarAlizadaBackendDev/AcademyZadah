package com.academy.repo;

import com.academy.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select count(l) from Lesson l where l.month.course.id = :courseId and l.published = true")
    long countPublishedInCourse(Long courseId);
}
