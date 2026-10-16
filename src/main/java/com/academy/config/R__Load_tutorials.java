package com.academy.config;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * Loads the lesson tutorials from classpath:curriculum/{course-slug}/m{month}/{NN}.md into the lessons.
 * Everything after the "---teacher---" line becomes the admin-only teacher guide.
 * Repeatable: Flyway re-runs it whenever any tutorial file changes (the files are the source of truth;
 * small edits can still be made in Admin → Courses, but changing a file overwrites that lesson month's text).
 */
@Component
public class R__Load_tutorials extends BaseJavaMigration {

    private static final Pattern PATH = Pattern.compile("curriculum/([a-z-]+)/m(\\d+)/(\\d+)\\.md$");
    private static final String TEACHER_MARKER = "\n---teacher---\n";

    @Override
    public Integer getChecksum() {
        CRC32 crc = new CRC32();
        for (Resource file : files()) {
            try {
                crc.update(file.getURL().toString().replaceAll(".*curriculum/", "").getBytes(StandardCharsets.UTF_8));
                crc.update(file.getContentAsByteArray());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return (int) crc.getValue();
    }

    @Override
    public void migrate(Context context) throws Exception {
        String sql = """
                UPDATE lesson SET content = ?, teacher_notes = ?, updated_at = CURRENT_TIMESTAMP
                WHERE sort_order = ? AND month_id = (SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id
                                                     WHERE c.slug = ? AND m.month_number = ?)""";
        try (PreparedStatement ps = context.getConnection().prepareStatement(sql)) {
            for (Resource file : files()) {
                Matcher m = PATH.matcher(file.getURL().toString());
                if (!m.find()) {
                    continue;
                }
                String text = file.getContentAsString(StandardCharsets.UTF_8).replace("\r\n", "\n");
                int split = text.indexOf(TEACHER_MARKER);
                ps.setString(1, (split < 0 ? text : text.substring(0, split)).strip());
                ps.setString(2, split < 0 ? null : text.substring(split + TEACHER_MARKER.length()).strip());
                ps.setInt(3, Integer.parseInt(m.group(3)) * 10);
                ps.setString(4, m.group(1));
                ps.setInt(5, Integer.parseInt(m.group(2)));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static Resource[] files() {
        try {
            Resource[] files = new PathMatchingResourcePatternResolver().getResources("classpath*:curriculum/*/m*/*.md");
            Arrays.sort(files, Comparator.comparing(f -> String.valueOf(f.getFilename()) + f.getDescription()));
            return files;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
