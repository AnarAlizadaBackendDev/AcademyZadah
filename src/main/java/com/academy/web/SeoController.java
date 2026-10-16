package com.academy.web;

import com.academy.config.AppProperties;
import com.academy.domain.BlogPost;
import com.academy.domain.Course;
import com.academy.domain.PostStatus;
import com.academy.repo.BlogPostRepository;
import com.academy.repo.CourseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeoController {

    private final AppProperties props;
    private final CourseRepository courses;
    private final BlogPostRepository posts;

    public SeoController(AppProperties props, CourseRepository courses, BlogPostRepository posts) {
        this.props = props;
        this.courses = courses;
        this.posts = posts;
    }

    @GetMapping(value = "/robots.txt", produces = "text/plain")
    public String robots() {
        return """
                User-agent: *
                Disallow: /admin
                Disallow: /dashboard
                Disallow: /account
                Sitemap: %s/sitemap.xml
                """.formatted(props.baseUrl());
    }

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public String sitemap() {
        StringBuilder xml = new StringBuilder("""
                <?xml version="1.0" encoding="UTF-8"?>
                <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                """);
        url(xml, "/");
        url(xml, "/blog");
        url(xml, "/contact");
        for (Course c : courses.findAllByOrderBySortOrder()) {
            url(xml, "/courses/" + c.getSlug());
        }
        for (BlogPost p : posts.findByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED, Pageable.unpaged())) {
            url(xml, "/blog/" + p.getSlug());
        }
        return xml.append("</urlset>\n").toString();
    }

    private void url(StringBuilder xml, String path) {
        xml.append("  <url><loc>").append(props.baseUrl()).append(path).append("</loc></url>\n");
    }
}
