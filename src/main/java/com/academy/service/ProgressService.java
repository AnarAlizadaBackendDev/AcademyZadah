package com.academy.service;

import com.academy.domain.LessonProgress;
import com.academy.repo.LessonProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProgressService {

    private final LessonProgressRepository progress;

    public ProgressService(LessonProgressRepository progress) {
        this.progress = progress;
    }

    @Transactional(readOnly = true)
    public Set<Long> completedLessonIds(Long userId) {
        return new HashSet<>(progress.findLessonIdsByUserId(userId));
    }

    /** Marks the lesson as done, or undone if it already was. Returns the new state. */
    @Transactional
    public boolean toggle(Long userId, Long lessonId) {
        if (progress.existsByUserIdAndLessonId(userId, lessonId)) {
            progress.deleteByUserIdAndLessonId(userId, lessonId);
            return false;
        }
        progress.save(new LessonProgress(userId, lessonId));
        return true;
    }
}
