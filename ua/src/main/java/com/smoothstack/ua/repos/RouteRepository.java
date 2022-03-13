package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Airport;
import com.smoothstack.ua.models.Route;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepository extends CrudRepository<Route, Integer> {
}
