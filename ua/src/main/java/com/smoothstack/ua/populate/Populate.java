package com.smoothstack.ua.populate;

import com.smoothstack.ua.models.Airport;
import com.smoothstack.ua.models.Route;
import com.smoothstack.ua.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

public class Populate {

    @Autowired
    AdminService adminService;

    public Populate() {
    }

    public void addRoutes(Integer amt) {
        List<Airport> airports = adminService.getAllAirports();
        Random rand = new Random(42);
        for(int i = 0; i < amt; i++) {
            Boolean quit = false;
            while(!quit) {
                Integer o = rand.nextInt(airports.size()+1);
                Integer d = rand.nextInt(airports.size()+1);
                if(o != d) {
                    Route route = new Route(airports.get(o), airports.get(d));
                    adminService.saveRoute(route);
                }
            }
        }
    }

    public void addFlights(Integer amt) {
        for(int i = 0; i < amt; i++) {

        }
    }
}
