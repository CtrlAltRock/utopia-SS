package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Airplane;
import com.smoothstack.ua.models.AirplaneType;
import com.smoothstack.ua.repos.AirplaneRepository;
import com.smoothstack.ua.repos.AirplaneTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirplaneService {

    Logger logger = LoggerFactory.getLogger(AirplaneService.class);

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AirplaneTypeRepository airplaneTypeRepository;

    public List<Airplane> getAllAirplanes() {
        logger.info("getting all airplanes");
        return airplaneRepository.findAll();
    }

    public Airplane getAirplaneById(Integer id) {
        Optional<Airplane> airplane = airplaneRepository.findById(id);
        if(airplane.isPresent()) return airplane.get();
        else return null;
    }

    public Airplane updateAirplane(Airplane airplane) {
        Airplane updated = airplaneRepository.save(airplane);
        return updated;
    }

    public Airplane saveAirplane(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    public void deleteAirplane(Airplane airplane) {
        airplaneRepository.delete(airplane);
    }

    public void deleteAirplaneById(Integer airplaneId) {
        airplaneRepository.deleteById(airplaneId);
    }

    public AirplaneType getAirplaneTypeById(Integer id) {
        Optional<AirplaneType> airplaneType = airplaneTypeRepository.findById(id);
        if(airplaneType.isPresent()) return airplaneType.get();
        else return null;
    }
}
