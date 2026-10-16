package com.academy.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseMonth month;

    private int sortOrder;
    private String title;
    private String content;
    private String videoUrl;
    /** Lesson plan, tips and solutions; visible only to the admin. */
    private String teacherNotes;
    /** Preview lessons are visible to everyone, so visitors can see the course style. */
    private boolean preview;
    private boolean published = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<LessonAttachment> attachments = new ArrayList<>();

    public Course getCourse() {
        return month.getCourse();
    }

    public Long getId() { return id; }
    public CourseMonth getMonth() { return month; }
    public void setMonth(CourseMonth month) { this.month = month; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getTeacherNotes() { return teacherNotes; }
    public void setTeacherNotes(String teacherNotes) { this.teacherNotes = teacherNotes; }
    public boolean isPreview() { return preview; }
    public void setPreview(boolean preview) { this.preview = preview; }
    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<LessonAttachment> getAttachments() { return attachments; }
}
