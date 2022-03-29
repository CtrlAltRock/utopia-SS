package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "select * from user_role where name = :name", nativeQuery = true)
    Optional<UserRole> findByName(@Param("name") String name);
}
