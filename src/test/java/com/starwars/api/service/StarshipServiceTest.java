package com.starwars.api.service;

import com.google.gson.Gson;
import com.starwars.api.domain.People.PeopleApiResponse;
import com.starwars.api.domain.People.PeopleApiResponseFilter;
import com.starwars.api.domain.People.PeopleResult;
import com.starwars.api.domain.Starships.StarshipApiResponse;
import com.starwars.api.domain.Starships.StarshipResult;
import com.starwars.api.service.impl.StarshipServiceImpl;
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
public class StarshipServiceTest {

    private StarshipServiceImpl starshipService;
    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    private Gson gson = new Gson();

    private StarshipResult startfighter = new StarshipResult("15","Startfighter",null);
    private StarshipResult startDestroyer = new StarshipResult("34","Star Destroyer", null);
    private StarshipResult imperialStarDestroyer = new StarshipResult("35","Imperial Star Destroyer",null);
    private List<StarshipResult>starshipResults = Arrays.asList(startfighter,startDestroyer,imperialStarDestroyer);


    @BeforeEach
    void setUp() {
        starshipService = new StarshipServiceImpl(mockHttpClient,gson);
    }

    @Test
    void getAllStarships_ShouldReturnCorrectPageContent_WhenDataExists() throws Exception {
        StarshipApiResponse apiResponse = new StarshipApiResponse("ok", starshipResults);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 2);
        Page<StarshipResult> resultPage = starshipService.getAllStarships(pageable);

        assertNotNull(resultPage, "La página no debe ser nula");
        assertEquals(3, resultPage.getTotalElements(), "Debe haber 3 elementos totales");
        assertEquals(2, resultPage.getSize(), "El tamaño de la página debe ser 2");
        assertEquals(2, resultPage.getContent().size(), "El contenido debe tener 2 elementos");
        assertTrue(resultPage.getContent().contains(startfighter), "Debe contener la nave 1");
        assertTrue(resultPage.getContent().contains(startDestroyer), "Debe contener la nave 2");
        assertFalse(resultPage.getContent().contains(imperialStarDestroyer), "No debe contener la nave 3");
    }

    @Test
    void getAllStarships_ShouldReturnEmptyPage_WhenApiReturnsNoData() throws Exception {
        StarshipApiResponse apiResponse = new StarshipApiResponse("ok", Collections.emptyList());
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 10);

        Page<StarshipResult> resultPage = starshipService.getAllStarships(pageable);

        assertTrue(resultPage.isEmpty(), "La página debe estar vacía");
        assertEquals(0, resultPage.getTotalElements(), "El total debe ser 0");
    }

    @Test
    void getAllStarships_ShouldHandleOutOfBoundsPageRequest() throws Exception {
        StarshipApiResponse apiResponse = new StarshipApiResponse("ok", starshipResults);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(10, 2);

        Page<StarshipResult> resultPage = starshipService.getAllStarships(pageable);

        assertTrue(resultPage.isEmpty(), "La página debe estar vacía si está fuera de límites");
        assertEquals(3, resultPage.getTotalElements(), "El total de elementos debe seguir siendo 3");
    }

    @Test
    void getById_ShouldReturnSingleStarshipResult_WhenValidUidIsProvided() throws Exception {

        String testUid = "15";
        PeopleResult starDestroyer = new PeopleResult(testUid, "Star Destroyer", null);

        PeopleApiResponseFilter apiResponse = new PeopleApiResponseFilter("ok", starDestroyer);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        StarshipResult result = starshipService.getById(testUid);

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(testUid, result.uid(), "El UID de la nave debe coincidir");
        assertEquals("Star Destroyer", result.description(), "El nombre/descripción de la nave debe coincidir");
    }

    @Test
    void getById_ShouldThrowException_WhenApiCallFails() throws Exception {

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException("Simulated Network Error"));

        assertThrows(IOException.class, () -> {
            starshipService.getById("1");
        }, "Debería lanzar IOException al fallar la red");
    }
}
