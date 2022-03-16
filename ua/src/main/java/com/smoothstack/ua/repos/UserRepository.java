package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
