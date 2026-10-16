package com.academy.config;

import com.academy.repo.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, LoginAttemptService loginAttempts) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/dashboard", "/account/**", "/lessons/*/complete").authenticated()
                        // Lessons and their files are public URLs; LessonController checks access itself,
                        // because preview lessons must be open to visitors.
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .addFilterBefore(new LoginBlockFilter(loginAttempts), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository users) {
        return email -> users.findByEmailIgnoreCase(email.trim())
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Rejects login attempts from an IP that failed too often recently. */
    static class LoginBlockFilter extends OncePerRequestFilter {

        private final LoginAttemptService loginAttempts;

        LoginBlockFilter(LoginAttemptService loginAttempts) {
            this.loginAttempts = loginAttempts;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws java.io.IOException, jakarta.servlet.ServletException {
            if ("POST".equals(request.getMethod()) && "/login".equals(request.getServletPath())
                    && loginAttempts.isBlocked(request.getRemoteAddr())) {
                response.sendRedirect(request.getContextPath() + "/login?blocked");
                return;
            }
            chain.doFilter(request, response);
        }
    }
}
