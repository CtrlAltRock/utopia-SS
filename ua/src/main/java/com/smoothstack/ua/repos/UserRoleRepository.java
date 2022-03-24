package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
