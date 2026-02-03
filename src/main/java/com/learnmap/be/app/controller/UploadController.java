package com.learnmap.be.app.controller;

import com.learnmap.be.domain.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    private final S3Service s3Service;
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping(
            value = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CompletableFuture<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return s3Service.uploadFile(file, "images");
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping(
            value = "/video",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CompletableFuture<String>  uploadVideo(@RequestParam("file") MultipartFile file) {
        return s3Service.uploadFile(file, "videos");
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping(
            value = "/document",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CompletableFuture<String>  uploadDocument(@RequestParam("file") MultipartFile file) {
        return s3Service.uploadFile(file, "documents");
    }
}
