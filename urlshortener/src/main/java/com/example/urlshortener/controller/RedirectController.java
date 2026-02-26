package com.example.urlshortener.controller;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class RedirectController {

    private final ShortUrlService service;

    public RedirectController(ShortUrlService service) {
        this.service = service;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        ShortUrl su = service.resolveAndTrack(code);
        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, URI.create(su.getOriginalUrl()).toString())
                .build();
    }
}