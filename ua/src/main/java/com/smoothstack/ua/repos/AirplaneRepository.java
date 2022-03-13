package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Airplane;
import org.springframework.data.repository.CrudRepository;

public interface AirplaneRepository extends CrudRepository<Airplane, Integer> {
}
