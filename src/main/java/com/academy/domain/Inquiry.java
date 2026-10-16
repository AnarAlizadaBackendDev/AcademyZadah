package com.academy.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/** A "contact me / I want to join" request from the public site. */
@Entity
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String courseInterest;
    private String message;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status = InquiryStatus.NEW;

    private String adminNote;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCourseInterest() { return courseInterest; }
    public void setCourseInterest(String courseInterest) { this.courseInterest = courseInterest; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public InquiryStatus getStatus() { return status; }
    public void setStatus(InquiryStatus status) { this.status = status; }
    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
