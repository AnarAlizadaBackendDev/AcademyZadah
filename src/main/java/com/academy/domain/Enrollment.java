package com.academy.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    /** Month N of the course unlocks on startDate + (N - 1) months. */
    private LocalDate startDate;
    /** Optional: access stops after this date. */
    private LocalDate endDate;
    private boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Enrollment() {
    }

    public Enrollment(User user, Course course, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValidOn(LocalDate day) {
        return active && (endDate == null || !day.isAfter(endDate));
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Course getCourse() { return course; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
