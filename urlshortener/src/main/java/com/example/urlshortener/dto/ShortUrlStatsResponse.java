package com.example.urlshortener.dto;

import java.time.Instant;

public class ShortUrlStatsResponse {
    private final String code;
    private final String originalUrl;
    private final Instant createdAt;
    private final long clicks;

    public ShortUrlStatsResponse(String code, String originalUrl, Instant createdAt, long clicks) {
        this.code = code;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.clicks = clicks;
    }

    public String getCode() { return code; }
    public String getOriginalUrl() { return originalUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public long getClicks() { return clicks; }
}