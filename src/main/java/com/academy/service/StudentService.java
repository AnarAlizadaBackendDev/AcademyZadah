package com.academy.service;

import com.academy.domain.Course;
import com.academy.domain.Enrollment;
import com.academy.domain.Role;
import com.academy.domain.User;
import com.academy.repo.CourseRepository;
import com.academy.repo.EnrollmentRepository;
import com.academy.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;

@Service
@Transactional
public class StudentService {

    private static final String PASSWORD_CHARS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UserRepository users;
    private final CourseRepository courses;
    private final EnrollmentRepository enrollments;
    private final PasswordEncoder encoder;

    public StudentService(UserRepository users, CourseRepository courses,
                          EnrollmentRepository enrollments, PasswordEncoder encoder) {
        this.users = users;
        this.courses = courses;
        this.enrollments = enrollments;
        this.encoder = encoder;
    }

    public record Created(User user, String password) {
    }

    public Created create(String fullName, String email, String phone, String notes, Long courseId, LocalDate startDate) {
        String normalizedEmail = email.trim().toLowerCase();
        if (users.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalArgumentException("admin.students.emailTaken");
        }
        String password = generatePassword();
        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(normalizedEmail);
        user.setPhone(blankToNull(phone));
        user.setNotes(blankToNull(notes));
        user.setRole(Role.STUDENT);
        user.setPasswordHash(encoder.encode(password));
        user.setMustChangePassword(true);
        users.save(user);
        if (courseId != null) {
            enroll(user.getId(), courseId, startDate != null ? startDate : LocalDate.now(), null);
        }
        return new Created(user, password);
    }

    public void update(Long id, String fullName, String email, String phone, String notes) {
        User user = get(id);
        String normalizedEmail = email.trim().toLowerCase();
        if (!user.getEmail().equals(normalizedEmail) && users.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalArgumentException("admin.students.emailTaken");
        }
        user.setFullName(fullName.trim());
        user.setEmail(normalizedEmail);
        user.setPhone(blankToNull(phone));
        user.setNotes(blankToNull(notes));
    }

    public String resetPassword(Long id) {
        String password = generatePassword();
        User user = get(id);
        user.setPasswordHash(encoder.encode(password));
        user.setMustChangePassword(true);
        return password;
    }

    public void toggleActive(Long id) {
        User user = get(id);
        user.setActive(!user.isActive());
    }

    public void delete(Long id) {
        users.delete(get(id));
    }

    public void enroll(Long userId, Long courseId, LocalDate startDate, LocalDate endDate) {
        if (enrollments.existsByUserIdAndCourseId(userId, courseId)) {
            throw new IllegalArgumentException("admin.students.alreadyEnrolled");
        }
        User user = get(userId);
        Course course = courses.findById(courseId).orElseThrow();
        Enrollment enrollment = new Enrollment(user, course, startDate, endDate);
        user.getEnrollments().add(enrollment);
        enrollments.save(enrollment);
    }

    public Long updateEnrollment(Long enrollmentId, LocalDate startDate, LocalDate endDate, boolean active) {
        Enrollment e = enrollments.findById(enrollmentId).orElseThrow();
        e.setStartDate(startDate);
        e.setEndDate(endDate);
        e.setActive(active);
        return e.getUser().getId();
    }

    public Long deleteEnrollment(Long enrollmentId) {
        Enrollment e = enrollments.findById(enrollmentId).orElseThrow();
        Long userId = e.getUser().getId();
        e.getUser().getEnrollments().remove(e);
        return userId;
    }

    /** A user changing their own password. Returns false if the current password is wrong. */
    public boolean changeOwnPassword(Long userId, String currentPassword, String newPassword) {
        User user = get(userId);
        if (!encoder.matches(currentPassword, user.getPasswordHash())) {
            return false;
        }
        user.setPasswordHash(encoder.encode(newPassword));
        user.setMustChangePassword(false);
        return true;
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        return users.findById(id).orElseThrow();
    }

    static String generatePassword() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }

    private static String blankToNull(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}
