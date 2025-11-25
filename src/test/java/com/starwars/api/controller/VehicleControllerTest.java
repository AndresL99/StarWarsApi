package com.starwars.api.controller;

import com.starwars.api.domain.Vehicles.VehicleResult;
import com.starwars.api.exception.ResourceNotFoundException;
import com.starwars.api.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleService vehicleService;

    private VehicleResult atst = new VehicleResult("55","AT-ST",null);
    private VehicleResult imperialSpeederBike = new VehicleResult("60","Imperial Speeder Bike", null);

    @Test
    void getAllVehicles_ShouldReturnPageOfVehicles_AndHttpStatusOk() throws Exception {
        List<VehicleResult> vehicleResults = Arrays.asList(atst,imperialSpeederBike);
        Pageable pageable = PageRequest.of(0, 10);
        Page<VehicleResult> mockedPage = new PageImpl<>(vehicleResults, pageable, 2);

        when(vehicleService.getAllVehicles(any(Pageable.class))).thenReturn(mockedPage);


        mockMvc.perform(get("/api/vehicles/")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_ShouldReturnSingleVehicle_AndHttpStatusOk() throws Exception {
        String testUid = "55";
        when(vehicleService.getById(eq(testUid))).thenReturn(atst);
        mockMvc.perform(get("/api/vehicles/{uid}", testUid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"uid\":\"55\", \"description\":\"AT-ST\"}"));
    }

    @Test
    void getById_ShouldReturnNotFound_WhenServiceThrowsResourceNotFoundException() throws Exception {
        when(vehicleService.getById(any(String.class)))
                .thenThrow(new ResourceNotFoundException("Vehicle con UID 999 no encontrado."));

        mockMvc.perform(get("/api/vehicles/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
