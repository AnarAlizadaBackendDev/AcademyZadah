package com.academy.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_progress")
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long lessonId;
    private LocalDateTime completedAt = LocalDateTime.now();

    protected LessonProgress() {
    }

    public LessonProgress(Long userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
    }

    public Long getLessonId() { return lessonId; }
}
