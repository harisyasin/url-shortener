package com.example.urlshortener.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "short_urls", indexes = {
        @Index(name = "idx_short_urls_code", columnList = "code", unique = true)
})
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String code;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private long clicks;

    protected ShortUrl() {}

    public ShortUrl(String code, String originalUrl) {
        this.code = code;
        this.originalUrl = originalUrl;
        this.createdAt = Instant.now();
        this.clicks = 0;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getOriginalUrl() { return originalUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public long getClicks() { return clicks; }

    public void incrementClicks() { this.clicks++; }
}