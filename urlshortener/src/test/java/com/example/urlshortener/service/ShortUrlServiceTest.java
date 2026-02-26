package com.example.urlshortener.service;

import com.example.urlshortener.repo.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class ShortUrlServiceTest {

    @Test
    void generatorProducesCorrectLength() {
        CodeGenerator gen = new CodeGenerator();
        String code = gen.generate(7);
        assertThat(code).hasSize(7);
    }

    @Test
    void createCallsSave() {
        ShortUrlRepository repo = Mockito.mock(ShortUrlRepository.class);
        Mockito.when(repo.existsByCode(Mockito.anyString())).thenReturn(false);

        ShortUrlService service = new ShortUrlService(repo, new CodeGenerator());
        service.create("https://example.com");

        Mockito.verify(repo).save(Mockito.any());
    }
}