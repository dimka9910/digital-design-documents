package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.jpa.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
