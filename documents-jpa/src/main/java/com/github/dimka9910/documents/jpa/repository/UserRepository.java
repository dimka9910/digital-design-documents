package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.jpa.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
}
