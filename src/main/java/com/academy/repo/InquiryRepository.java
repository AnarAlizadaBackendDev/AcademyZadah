package com.academy.repo;

import com.academy.domain.Inquiry;
import com.academy.domain.InquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findAllByOrderByCreatedAtDesc();

    List<Inquiry> findTop5ByOrderByCreatedAtDesc();

    long countByStatus(InquiryStatus status);
}
