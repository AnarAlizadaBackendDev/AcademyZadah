package com.academy.repo;

import com.academy.domain.Role;
import com.academy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByRole(Role role);

    List<User> findAllByRoleOrderByCreatedAtDesc(Role role);

    long countByRoleAndActiveTrue(Role role);
}
