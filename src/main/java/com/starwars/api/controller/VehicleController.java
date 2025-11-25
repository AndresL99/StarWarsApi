package com.starwars.api.controller;

import com.starwars.api.domain.Vehicles.VehicleResult;
import com.starwars.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/vehicles/")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('AUTHORIZED')")
    public ResponseEntity<Page<VehicleResult>>getAllVehicle(@PageableDefault(size = 2, page = 0) Pageable pageable) throws IOException, InterruptedException {
        //verifyAuth(authentication);
        Page<VehicleResult>vehicleResultPage = vehicleService.getAllVehicles(pageable);
        return ResponseEntity.ok(vehicleResultPage);
    }

    @GetMapping(value = "/{uid}")
    @PreAuthorize(value = "hasAnyAuthority('AUTHORIZED')")
    public ResponseEntity<VehicleResult>getById(@PathVariable String uid) throws IOException, InterruptedException {
        com.starwars.api.domain.Vehicles.VehicleResult vehicleResult = vehicleService.getById(uid);
        return ResponseEntity.ok(vehicleResult);
    }

    /*private void verifyAuth(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getUserGranted())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }*/
}
