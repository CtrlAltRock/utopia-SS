package com.smoothstack.ua.security.repositories;

import org.springframework.data.repository.CrudRepository;
import com.smoothstack.ua.security.models.DAOUser;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
}