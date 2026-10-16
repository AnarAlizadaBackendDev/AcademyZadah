package com.academy.service;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Lessons and blog posts are written in Markdown (only the admin writes them, so raw HTML is allowed). */
@Service
public class MarkdownService {

    private static final Pattern YOUTUBE = Pattern.compile(
            "(?:youtube\\.com/(?:watch\\?v=|embed/|shorts/)|youtu\\.be/)([A-Za-z0-9_-]{6,})");
    private static final Pattern VIMEO = Pattern.compile("vimeo\\.com/(?:video/)?(\\d+)");

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownService() {
        List<Extension> extensions = List.of(TablesExtension.create());
        this.parser = Parser.builder().extensions(extensions).build();
        this.renderer = HtmlRenderer.builder().extensions(extensions).build();
    }

    public String toHtml(String markdown) {
        return markdown == null ? "" : renderer.render(parser.parse(markdown));
    }

    /** Converts a YouTube/Vimeo link into an embeddable player URL, or null if unknown. */
    public String videoEmbedUrl(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        Matcher yt = YOUTUBE.matcher(url);
        if (yt.find()) {
            return "https://www.youtube-nocookie.com/embed/" + yt.group(1);
        }
        Matcher vimeo = VIMEO.matcher(url);
        if (vimeo.find()) {
            return "https://player.vimeo.com/video/" + vimeo.group(1);
        }
        return null;
    }
}
