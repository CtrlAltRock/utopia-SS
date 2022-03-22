package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {

    @Query(value = "select * from airplane where airplane.type_id = :airplane_type_id", nativeQuery = true)
    List<Airplane> findByTypeId(@Param("airplane_type_id") Integer airplane_type_id);

    @Query(value = "select * from airplane inner join airplane_type on airplane_type.id = airplane.type_id and airplane_type.max_capacity = :max_capacity", nativeQuery = true)
    List<Airplane> findByMaxCapacity(@Param("max_capacity") Integer max_capacity);
}
