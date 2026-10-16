package com.academy.web.admin;

import com.academy.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/** Image upload used by the drag & drop editor and cover image fields. */
@RestController
public class AdminUploadController {

    private final StorageService storage;

    public AdminUploadController(StorageService storage) {
        this.storage = storage;
    }

    @PostMapping("/admin/uploads/image")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("image") MultipartFile image) {
        try {
            return ResponseEntity.ok(Map.of("url", storage.storePublicImage(image)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
