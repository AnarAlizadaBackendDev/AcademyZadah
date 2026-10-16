package com.academy.config;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Blocks an IP for 15 minutes after 5 failed logins (in-memory, enough for a single instance). */
@Component
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration BLOCK_TIME = Duration.ofMinutes(15);

    private record Attempts(int count, Instant last) {
    }

    private final Map<String, Attempts> attempts = new ConcurrentHashMap<>();

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        if (event.getAuthentication().getDetails() instanceof WebAuthenticationDetails d) {
            attempts.merge(d.getRemoteAddress(), new Attempts(1, Instant.now()),
                    (old, one) -> new Attempts(isExpired(old) ? 1 : old.count() + 1, Instant.now()));
        }
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        if (event.getAuthentication().getDetails() instanceof WebAuthenticationDetails d) {
            attempts.remove(d.getRemoteAddress());
        }
    }

    public boolean isBlocked(String ip) {
        Attempts a = attempts.get(ip);
        if (a == null) {
            return false;
        }
        if (isExpired(a)) {
            attempts.remove(ip);
            return false;
        }
        return a.count() >= MAX_ATTEMPTS;
    }

    private boolean isExpired(Attempts a) {
        return a.last().plus(BLOCK_TIME).isBefore(Instant.now());
    }
}
