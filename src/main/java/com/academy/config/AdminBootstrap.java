package com.academy.config;

import com.academy.domain.Role;
import com.academy.domain.User;
import com.academy.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/** Creates the first admin account from ADMIN_EMAIL / ADMIN_PASSWORD if no admin exists yet. */
@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AppProperties props;

    public AdminBootstrap(UserRepository users, PasswordEncoder encoder, AppProperties props) {
        this.users = users;
        this.encoder = encoder;
        this.props = props;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (users.existsByRole(Role.ADMIN)) {
            return;
        }
        AppProperties.Admin admin = props.admin();
        if (!StringUtils.hasText(admin.email()) || !StringUtils.hasText(admin.password())) {
            log.warn("No admin account exists. Set ADMIN_EMAIL and ADMIN_PASSWORD to create one.");
            return;
        }
        User user = new User();
        user.setFullName(StringUtils.hasText(admin.name()) ? admin.name() : "Admin");
        user.setEmail(admin.email().trim().toLowerCase());
        user.setPasswordHash(encoder.encode(admin.password()));
        user.setRole(Role.ADMIN);
        users.save(user);
        log.info("Admin account created: {}", user.getEmail());
    }
}
