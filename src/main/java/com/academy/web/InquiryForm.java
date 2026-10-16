package com.academy.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InquiryForm {

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Email
    @Size(max = 160)
    private String email;

    @Size(max = 40)
    private String phone;

    @Size(max = 60)
    private String course;

    @Size(max = 2000)
    private String message;

    /** Honeypot: hidden field that only bots fill in. */
    private String website;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
