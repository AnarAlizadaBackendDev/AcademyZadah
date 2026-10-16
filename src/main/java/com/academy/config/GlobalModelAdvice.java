package com.academy.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/** Values every page layout needs (brand, contacts, current user, language). */
@ControllerAdvice
public class GlobalModelAdvice {

    private final AppProperties props;

    public GlobalModelAdvice(AppProperties props) {
        this.props = props;
    }

    @ModelAttribute("app")
    public AppProperties app() {
        return props;
    }

    @ModelAttribute("me")
    public AppUserDetails me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof AppUserDetails user ? user : null;
    }

    @ModelAttribute("currentPath")
    public String currentPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("currentLang")
    public String currentLang() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        return WebConfig.LANGUAGES.contains(lang) ? lang : "az";
    }

    @ModelAttribute("languages")
    public List<String> languages() {
        return WebConfig.LANGUAGES;
    }
}
