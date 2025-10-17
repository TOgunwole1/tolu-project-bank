package com.mudson.repository;

import com.mudson.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for User entity to handle database operations.
 */
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * Find a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the user if found.
     */
    //Optional<User> findByEmail(String email);

    /**
     * Find users by their first name.
     *
     * @param firstName The first name of the users.
     * @return A list of users with the given first name.
     */
    List<User> findByFirstName(String firstName);

    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    Boolean existsByAddress(String address);
}
