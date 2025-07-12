package com.escola.auth.repository;

import com.escola.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their username. This method is essential for the
     * UserDetailsService to load the user during the authentication process.
     *
     * @param username the username to search for.
     * @return an Optional containing the user if found, or an empty Optional otherwise.
     */
    Optional<User> findByUsername(String username);
}