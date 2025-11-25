package com.starwars.api.service;


import com.google.gson.Gson;
import com.starwars.api.domain.People.PeopleApiResponse;
import com.starwars.api.domain.People.PeopleApiResponseFilter;
import com.starwars.api.domain.People.PeopleResult;
import com.starwars.api.service.impl.PeopleServiceImpl;
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
public class PeopleServiceTest {

    private PeopleServiceImpl peopleService;

    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    private Gson gson = new Gson();

    private PeopleResult darthVader = new PeopleResult("10", "Darth Vader", null);
    private PeopleResult lukeSkywalker = new PeopleResult("20", "Luke Skywalker", null);
    private PeopleResult hanSolo = new PeopleResult("30", "Han Solo", null);
    private List<PeopleResult> peopleResults = Arrays.asList(darthVader, lukeSkywalker, hanSolo);

    @BeforeEach
    void setUp() {
        peopleService = new PeopleServiceImpl(mockHttpClient, gson);
    }

    @Test
    void getAllPeople_ShouldReturnCorrectPageContent_WhenDataExists() throws Exception {
        PeopleApiResponse apiResponse = new PeopleApiResponse("ok", peopleResults);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 2);
        Page<PeopleResult> resultPage = peopleService.getAllPeople(pageable);

        assertNotNull(resultPage, "La página no debe ser nula");
        assertEquals(3, resultPage.getTotalElements(), "Debe haber 3 elementos totales");
        assertEquals(2, resultPage.getSize(), "El tamaño de la página debe ser 2");
        assertEquals(2, resultPage.getContent().size(), "El contenido debe tener 2 elementos");
        assertTrue(resultPage.getContent().contains(darthVader), "Debe contener el personaje 1");
        assertTrue(resultPage.getContent().contains(lukeSkywalker), "Debe contener el personaje 2");
        assertFalse(resultPage.getContent().contains(hanSolo), "No debe contener el personaje 3");
    }

    @Test
    void getAllPeople_ShouldReturnEmptyPage_WhenApiReturnsNoData() throws Exception {
        PeopleApiResponse apiResponse = new PeopleApiResponse("ok", Collections.emptyList());
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 10);


        Page<PeopleResult> resultPage = peopleService.getAllPeople(pageable);


        assertTrue(resultPage.isEmpty(), "La página debe estar vacía");
        assertEquals(0, resultPage.getTotalElements(), "El total debe ser 0");
    }

    @Test
    void getAllPeople_ShouldHandleOutOfBoundsPageRequest() throws Exception {
        PeopleApiResponse apiResponse = new PeopleApiResponse("ok", peopleResults);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(10, 2);


        Page<PeopleResult> resultPage = peopleService.getAllPeople(pageable);


        assertTrue(resultPage.isEmpty(), "La página debe estar vacía si está fuera de límites");
        assertEquals(3, resultPage.getTotalElements(), "El total de elementos debe seguir siendo 3");
    }

    @Test
    void getById_ShouldReturnSinglePeopleResult_WhenValidUidIsProvided() throws Exception {

        String testUid = "10";
        PeopleResult darthVader = new PeopleResult(testUid, "Darth Vader", null);

        PeopleApiResponseFilter apiResponse = new PeopleApiResponseFilter("ok", darthVader);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        PeopleResult result = peopleService.getById(testUid);

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(testUid, result.uid(), "El UID del personaje debe coincidir");
        assertEquals("Darth Vader", result.description(), "El nombre/descripción del personaje debe coincidir");
    }

    @Test
    void getById_ShouldThrowException_WhenApiCallFails() throws Exception {

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException("Simulated Network Error"));

        assertThrows(IOException.class, () -> {
            peopleService.getById("10");
        }, "Debería lanzar IOException al fallar la red");
    }
}
