package com.academy.service;

import com.academy.domain.BlogPost;
import com.academy.domain.PostStatus;
import com.academy.repo.BlogPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class BlogService {

    private final BlogPostRepository posts;

    public BlogService(BlogPostRepository posts) {
        this.posts = posts;
    }

    /** Creates (post.id == null) or updates a post; generates a unique slug from the title if empty. */
    public BlogPost save(BlogPost post, String requestedSlug) {
        String base = Slugs.of(requestedSlug == null || requestedSlug.isBlank() ? post.getTitle() : requestedSlug);
        if (!base.equals(post.getSlug())) {
            String slug = base;
            for (int i = 2; posts.existsBySlug(slug); i++) {
                slug = base + "-" + i;
            }
            post.setSlug(slug);
        }
        if (post.getStatus() == PostStatus.PUBLISHED && post.getPublishedAt() == null) {
            post.setPublishedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return posts.save(post);
    }
}
