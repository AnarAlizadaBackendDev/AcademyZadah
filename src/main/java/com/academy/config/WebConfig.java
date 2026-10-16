package com.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final List<String> LANGUAGES = List.of("az", "ru", "en");

    private final AppProperties props;
    private final AccountStateInterceptor accountStateInterceptor;

    public WebConfig(AppProperties props, AccountStateInterceptor accountStateInterceptor) {
        this.props = props;
        this.accountStateInterceptor = accountStateInterceptor;
    }

    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver("lang");
        resolver.setDefaultLocale(Locale.of("az"));
        resolver.setCookieMaxAge(Duration.ofDays(365));
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChange = new LocaleChangeInterceptor();
        localeChange.setParamName("lang");
        localeChange.setIgnoreInvalidLocale(true);
        registry.addInterceptor(localeChange);
        registry.addInterceptor(accountStateInterceptor)
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/media/**", "/login", "/logout", "/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Public uploads (blog images). Lesson files are private and served by LessonController.
        String publicDir = Path.of(props.storageDir(), "public").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/media/**")
                .addResourceLocations(publicDir)
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic());
    }
}
