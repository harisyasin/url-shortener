package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortenRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.profiles.active=test",
                "app.base-url=http://localhost"
        }
)
class ShortUrlIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void shortenReturnsCodeAndShortUrl() {
        ShortenRequest req = new ShortenRequest();
        req.setUrl("https://example.com");

        ResponseEntity<String> res = rest.postForEntity("/api/shorten", req, String.class);

        assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(res.getBody()).contains("code").contains("shortUrl");
    }
}