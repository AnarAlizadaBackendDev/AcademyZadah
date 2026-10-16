package com.academy.service;

import com.academy.domain.Course;

import java.time.LocalDate;

/**
 * What a student may open in one course.
 * Months unlock one by one: month N opens on startDate + (N - 1) months,
 * unless allMonthsOpen (e.g. Essentials materials for an Advanced student).
 */
public record CourseAccess(Course course, LocalDate startDate, boolean allMonthsOpen) {

    public LocalDate unlockDate(int monthNumber) {
        return allMonthsOpen ? startDate : startDate.plusMonths(monthNumber - 1L);
    }

    public boolean isMonthOpen(int monthNumber, LocalDate today) {
        return !today.isBefore(unlockDate(monthNumber));
    }

    /** Keeps the more generous of two accesses to the same course. */
    CourseAccess merge(CourseAccess other) {
        if (allMonthsOpen != other.allMonthsOpen) {
            return allMonthsOpen ? this : other;
        }
        return startDate.isAfter(other.startDate) ? other : this;
    }
}
