package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortenRequest;
import com.example.urlshortener.dto.ShortenResponse;
import com.example.urlshortener.dto.ShortUrlStatsResponse;
import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShortUrlController {

    private final ShortUrlService service;

    @Value("${app.base-url}")
    private String baseUrl;

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest req) {
        ShortUrl created = service.create(req.getUrl());
        String shortUrl = baseUrl.replaceAll("/+$", "") + "/" + created.getCode();
        return ResponseEntity.ok(new ShortenResponse(created.getCode(), shortUrl));
    }

    @GetMapping("/stats/{code}")
    public ResponseEntity<ShortUrlStatsResponse> stats(@PathVariable String code) {
        ShortUrl su = service.getStats(code);
        return ResponseEntity.ok(new ShortUrlStatsResponse(
                su.getCode(), su.getOriginalUrl(), su.getCreatedAt(), su.getClicks()
        ));
    }
}