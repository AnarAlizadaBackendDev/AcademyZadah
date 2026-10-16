package com.academy.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;
    private String title;
    private int durationMonths;
    private int sortOrder;

    /** Students of this course also get full access to the included course. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "includes_course_id")
    private Course includesCourse;

    @OneToMany(mappedBy = "course")
    @OrderBy("monthNumber")
    private List<CourseMonth> months = new ArrayList<>();

    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getDurationMonths() { return durationMonths; }
    public int getSortOrder() { return sortOrder; }
    public Course getIncludesCourse() { return includesCourse; }
    public List<CourseMonth> getMonths() { return months; }
}
