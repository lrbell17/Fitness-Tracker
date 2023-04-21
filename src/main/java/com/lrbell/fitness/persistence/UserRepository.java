package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findDistinctByUserName(final String userName);
}
