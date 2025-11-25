package com.starwars.api.service;

import com.starwars.api.domain.Vehicles.VehicleResult;
import com.starwars.api.domain.Vehicles.Vehicles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface VehicleService {

    Page<VehicleResult> getAllVehicles(Pageable pageable) throws IOException, InterruptedException;
    VehicleResult getById(String uid) throws IOException, InterruptedException;
    VehicleResult getByName(String name);
}
