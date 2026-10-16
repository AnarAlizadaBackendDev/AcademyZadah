package com.academy.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_attachment")
public class LessonAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    private String originalName;
    private String storedName;
    private String contentType;
    private long sizeBytes;
    private LocalDateTime createdAt = LocalDateTime.now();

    protected LessonAttachment() {
    }

    public LessonAttachment(Lesson lesson, String originalName, String storedName, String contentType, long sizeBytes) {
        this.lesson = lesson;
        this.originalName = originalName;
        this.storedName = storedName;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
    }

    public Long getId() { return id; }
    public Lesson getLesson() { return lesson; }
    public String getOriginalName() { return originalName; }
    public String getStoredName() { return storedName; }
    public String getContentType() { return contentType; }
    public long getSizeBytes() { return sizeBytes; }
}
