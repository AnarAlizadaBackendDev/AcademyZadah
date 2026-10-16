package com.academy.config;

import com.academy.domain.User;
import com.academy.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * For logged-in users: logs out deactivated accounts immediately and
 * forces a password change after the admin created or reset the account.
 */
@Component
public class AccountStateInterceptor implements HandlerInterceptor {

    private final UserRepository users;

    public AccountStateInterceptor(UserRepository users) {
        this.users = users;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AppUserDetails me)) {
            return true;
        }
        User user = users.findById(me.getId()).orElse(null);
        if (user == null || !user.isActive()) {
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();
            response.sendRedirect("/login?disabled");
            return false;
        }
        if (user.isMustChangePassword() && !request.getRequestURI().startsWith("/account/password")) {
            response.sendRedirect("/account/password");
            return false;
        }
        return true;
    }
}
