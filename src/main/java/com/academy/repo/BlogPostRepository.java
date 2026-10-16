package com.academy.repo;

import com.academy.domain.BlogPost;
import com.academy.domain.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    Optional<BlogPost> findBySlug(String slug);

    boolean existsBySlug(String slug);

    Page<BlogPost> findByStatusOrderByPublishedAtDesc(PostStatus status, Pageable pageable);

    Page<BlogPost> findByStatusAndLangOrderByPublishedAtDesc(PostStatus status, String lang, Pageable pageable);

    List<BlogPost> findAllByOrderByCreatedAtDesc();

    long countByStatus(PostStatus status);
}
