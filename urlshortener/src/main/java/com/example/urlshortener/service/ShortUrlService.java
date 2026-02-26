package com.example.urlshortener.service;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.repo.ShortUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShortUrlService {

    private final ShortUrlRepository repo;
    private final CodeGenerator generator;

    public ShortUrlService(ShortUrlRepository repo, CodeGenerator generator) {
        this.repo = repo;
        this.generator = generator;
    }

    @Transactional
    public ShortUrl create(String originalUrl) {
        String code = generateUniqueCode(7);
        return repo.save(new ShortUrl(code, originalUrl));
    }

    @Transactional
    public ShortUrl resolveAndTrack(String code) {
        ShortUrl su = repo.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Short code not found"));
        su.incrementClicks(); // JPA will persist on transaction commit
        return su;
    }

    @Transactional(readOnly = true)
    public ShortUrl getStats(String code) {
        return repo.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Short code not found"));
    }

    private String generateUniqueCode(int length) {
        for (int i = 0; i < 10; i++) {
            String code = generator.generate(length);
            if (!repo.existsByCode(code)) return code;
        }
        String code;
        do {
            code = generator.generate(length + 2);
        } while (repo.existsByCode(code));
        return code;
    }
}