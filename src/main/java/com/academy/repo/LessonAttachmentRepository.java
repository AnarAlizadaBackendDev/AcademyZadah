package com.academy.repo;

import com.academy.domain.LessonAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonAttachmentRepository extends JpaRepository<LessonAttachment, Long> {
}
