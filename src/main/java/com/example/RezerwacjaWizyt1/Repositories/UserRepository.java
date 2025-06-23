package com.example.RezerwacjaWizyt1.Repositories;

import com.example.RezerwacjaWizyt1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
