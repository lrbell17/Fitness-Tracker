package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository for database operations on {@link User} entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Uses the userName to get the user.
     *
     * @param userName The username.
     * @return The {@link User} corresponding to the userName.
     */
    User findDistinctByUserName(final String userName);
}
