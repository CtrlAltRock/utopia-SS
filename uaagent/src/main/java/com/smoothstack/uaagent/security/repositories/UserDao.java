package com.smoothstack.uaagent.security.repositories;

import com.smoothstack.uaagent.security.models.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
}