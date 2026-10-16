package com.academy.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlugsTest {

    @Test
    void transliteratesAllSiteLanguages() {
        assertThat(Slugs.of("Java 25: Yeni xüsusiyyətlər!")).isEqualTo("java-25-yeni-xususiyyetler");
        assertThat(Slugs.of("Что нового в Spring Boot 4")).isEqualTo("chto-novogo-v-spring-boot-4");
        assertThat(Slugs.of("  Virtual Threads  ")).isEqualTo("virtual-threads");
        assertThat(Slugs.of("!!!")).isEqualTo("post");
    }
}
