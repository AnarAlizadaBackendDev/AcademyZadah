package com.academy.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CourseAccessTest {

    private final LocalDate start = LocalDate.of(2026, 1, 15);

    @Test
    void monthsUnlockOneByOne() {
        CourseAccess access = new CourseAccess(null, start, false);

        assertThat(access.isMonthOpen(1, start)).isTrue();
        assertThat(access.isMonthOpen(2, start)).isFalse();
        assertThat(access.isMonthOpen(2, LocalDate.of(2026, 2, 14))).isFalse();
        assertThat(access.isMonthOpen(2, LocalDate.of(2026, 2, 15))).isTrue();
        assertThat(access.unlockDate(4)).isEqualTo(LocalDate.of(2026, 4, 15));
    }

    @Test
    void nothingOpensBeforeStartDate() {
        CourseAccess access = new CourseAccess(null, start, false);
        assertThat(access.isMonthOpen(1, start.minusDays(1))).isFalse();
    }

    @Test
    void includedCourseIsFullyOpen() {
        CourseAccess access = new CourseAccess(null, start, true);
        assertThat(access.isMonthOpen(4, start)).isTrue();
    }

    @Test
    void mergeKeepsTheMoreGenerousAccess() {
        CourseAccess direct = new CourseAccess(null, start, false);
        CourseAccess included = new CourseAccess(null, start.plusMonths(3), true);
        assertThat(direct.merge(included)).isSameAs(included);
        assertThat(included.merge(direct)).isSameAs(included);
    }
}
