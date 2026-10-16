package com.academy.repo;

import com.academy.domain.CourseMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMonthRepository extends JpaRepository<CourseMonth, Long> {
}
