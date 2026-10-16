package com.academy.repo;

import com.academy.domain.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    @Query("select p.lessonId from LessonProgress p where p.userId = :userId")
    List<Long> findLessonIdsByUserId(Long userId);

    boolean existsByUserIdAndLessonId(Long userId, Long lessonId);

    @Modifying
    @Query("delete from LessonProgress p where p.userId = :userId and p.lessonId = :lessonId")
    void deleteByUserIdAndLessonId(Long userId, Long lessonId);
}
