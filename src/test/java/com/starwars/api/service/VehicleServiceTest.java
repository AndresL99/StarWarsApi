package com.starwars.api.service;

import com.google.gson.Gson;
import com.starwars.api.domain.People.PeopleApiResponseFilter;
import com.starwars.api.domain.People.PeopleResult;
import com.starwars.api.domain.Starships.StarshipApiResponse;
import com.starwars.api.domain.Starships.StarshipResult;
import com.starwars.api.domain.Vehicles.VehicleApiResponse;
import com.starwars.api.domain.Vehicles.VehicleApiResponseFilter;
import com.starwars.api.domain.Vehicles.VehicleResult;
import com.starwars.api.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    private VehicleServiceImpl vehicleService;

    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    private Gson gson = new Gson();

    private VehicleResult atst = new VehicleResult("55","AT-ST",null);
    private VehicleResult imperialSpeederBike = new VehicleResult("60","Imperial Speeder Bike", null);
    private VehicleResult jediStarFighter = new VehicleResult("66","Jedi StarFigher", null);
    private List<VehicleResult>vehicleResults = Arrays.asList(atst,imperialSpeederBike,jediStarFighter);

    @BeforeEach
    void setUp(){
        vehicleService = new VehicleServiceImpl(mockHttpClient,gson);
    }

    @Test
    void getAllVehicles_ShouldReturnCorrectPageContent_WhenDataExists() throws Exception {
        VehicleApiResponse apiResponse = new VehicleApiResponse("ok", vehicleResults);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 2);
        Page<VehicleResult> resultPage = vehicleService.getAllVehicles(pageable);

        assertNotNull(resultPage, "La página no debe ser nula");
        assertEquals(3, resultPage.getTotalElements(), "Debe haber 3 elementos totales");
        assertEquals(2, resultPage.getSize(), "El tamaño de la página debe ser 2");
        assertEquals(2, resultPage.getContent().size(), "El contenido debe tener 2 elementos");
        assertTrue(resultPage.getContent().contains(atst), "Debe contener el vehiculo 1");
        assertTrue(resultPage.getContent().contains(imperialSpeederBike), "Debe contener el vehiculo 2");
        assertFalse(resultPage.getContent().contains(jediStarFighter), "No debe contener el vehiculo 3");
    }

    @Test
    void getAllVehicles_ShouldReturnEmptyPage_WhenApiReturnsNoData() throws Exception {
        VehicleApiResponse apiResponse = new VehicleApiResponse("ok", Collections.emptyList());
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 10);

        Page<VehicleResult> resultPage = vehicleService.getAllVehicles(pageable);

        assertTrue(resultPage.isEmpty(), "La página debe estar vacía");
        assertEquals(0, resultPage.getTotalElements(), "El total debe ser 0");
    }

    @Test
    void getAllVehicles_ShouldHandleOutOfBoundsPageRequest() throws Exception {
        VehicleApiResponse apiResponse = new VehicleApiResponse("ok", vehicleResults);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(10, 2);

        Page<VehicleResult> resultPage = vehicleService.getAllVehicles(pageable);

        assertTrue(resultPage.isEmpty(), "La página debe estar vacía si está fuera de límites");
        assertEquals(3, resultPage.getTotalElements(), "El total de elementos debe seguir siendo 3");
    }

    @Test
    void getById_ShouldReturnVehicleResult_WhenValidUidIsProvided() throws Exception {

        String testUid = "60";
        VehicleResult imperialSpeederBike = new VehicleResult(testUid, "Imperial Speeder Bike", null);

        VehicleApiResponseFilter apiResponse = new VehicleApiResponseFilter("ok", imperialSpeederBike);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        VehicleResult result = vehicleService.getById(testUid);

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(testUid, result.uid(), "El UID del vehiculo debe coincidir");
        assertEquals("Imperial Speeder Bike", result.description(), "El nombre/descripción del vehiculo debe coincidir");
    }

    @Test
    void getById_ShouldThrowException_WhenApiCallFails() throws Exception {

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException("Simulated Network Error"));

        assertThrows(IOException.class, () -> {
            vehicleService.getById("1");
        }, "Debería lanzar IOException al fallar la red");
    }
}
