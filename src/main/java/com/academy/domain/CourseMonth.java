package com.academy.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_month")
public class CourseMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    private int monthNumber;
    private String title;
    private String description;

    @OneToMany(mappedBy = "month", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder, id")
    private List<Lesson> lessons = new ArrayList<>();

    public Long getId() { return id; }
    public Course getCourse() { return course; }
    public int getMonthNumber() { return monthNumber; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Lesson> getLessons() { return lessons; }
}
