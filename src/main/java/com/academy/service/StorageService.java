package com.academy.service;

import com.academy.config.AppProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Stores uploads on disk (a Railway volume in production).
 * public/  -> served as /media/** (blog images)
 * private/ -> lesson files, only served after an access check
 */
@Service
public class StorageService {

    private static final Set<String> IMAGE_EXTENSIONS = Set.of("png", "jpg", "jpeg", "gif", "webp");

    private final Path publicDir;
    private final Path privateDir;

    public StorageService(AppProperties props) {
        Path root = Path.of(props.storageDir()).toAbsolutePath();
        this.publicDir = root.resolve("public");
        this.privateDir = root.resolve("private");
        try {
            Files.createDirectories(publicDir);
            Files.createDirectories(privateDir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Returns the public URL of the stored image. */
    public String storePublicImage(MultipartFile file) {
        String ext = extension(file.getOriginalFilename());
        if (!IMAGE_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("Only png, jpg, gif and webp images are allowed");
        }
        String name = UUID.randomUUID() + "." + ext;
        save(file, publicDir.resolve(name));
        return "/media/" + name;
    }

    /** Returns the stored file name (to keep in the database). */
    public String storePrivate(MultipartFile file) {
        String ext = extension(file.getOriginalFilename());
        String name = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
        save(file, privateDir.resolve(name));
        return name;
    }

    public Path privateFile(String storedName) {
        Path path = privateDir.resolve(storedName).normalize();
        if (!path.startsWith(privateDir)) {
            throw new IllegalArgumentException("Invalid file name");
        }
        return path;
    }

    public void deletePrivate(String storedName) {
        try {
            Files.deleteIfExists(privateFile(storedName));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void save(MultipartFile file, Path target) {
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String extension(String filename) {
        String ext = StringUtils.getFilenameExtension(filename);
        if (ext == null) {
            return "";
        }
        ext = ext.toLowerCase(Locale.ROOT);
        return ext.matches("[a-z0-9]{1,8}") ? ext : "";
    }
}
