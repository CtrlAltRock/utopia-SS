package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
