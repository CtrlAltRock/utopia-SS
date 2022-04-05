package com.smoothstack.ua.services;

import com.smoothstack.ua.models.AirplaneType;
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
public class AirplaneTypeService {

    Logger logger = LoggerFactory.getLogger(AirplaneTypeService.class);

    @Autowired
    AirplaneTypeRepository airplaneTypeRepository;

    public List<AirplaneType> getAllAirplaneTypes() {
        return (List<AirplaneType>) airplaneTypeRepository.findAll();
    }

    public AirplaneType getAirplaneTypeById(Integer id) {
        Optional<AirplaneType> airplaneType = airplaneTypeRepository.findById(id);
        if(airplaneType.isPresent()) return airplaneType.get();
        return null;
    }

    public void saveAirplaneTypes(List<AirplaneType> airplaneTypes) {
        airplaneTypeRepository.saveAll(airplaneTypes);
    }

    public AirplaneType saveAirplaneType(AirplaneType airplaneType) { AirplaneType posted = airplaneTypeRepository.save(airplaneType);
        return posted;
    }

    public void deleteAirplaneTypes(List<AirplaneType> airplaneTypes) { airplaneTypeRepository.deleteAll(airplaneTypes);}

    public void deleteAirplaneTypeById(Integer id) { airplaneTypeRepository.deleteById(id); }

}
