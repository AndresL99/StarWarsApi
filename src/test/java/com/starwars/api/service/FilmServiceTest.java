package com.starwars.api.service;

import com.google.gson.Gson;
import com.starwars.api.domain.Films.FilmApiResponse;
import com.starwars.api.domain.Films.FilmApiResponseFilter;
import com.starwars.api.domain.Films.FilmsResult;
import com.starwars.api.service.impl.FilmServiceImpl;
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
public class FilmServiceTest {

    private FilmServiceImpl filmService;

    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    private Gson gson = new Gson();

    private FilmsResult film1 = new FilmsResult("1", "A New Hope", null);
    private FilmsResult film2 = new FilmsResult("2", "The Empire Strikes Back", null);
    private FilmsResult film3 = new FilmsResult("3", "Return of the Jedi", null);
    private List<FilmsResult> allFilmsList = Arrays.asList(film1, film2, film3);

    @BeforeEach
    void setUp() {
        filmService = new FilmServiceImpl(mockHttpClient, gson);
    }

    @Test
    void getAllFilms_ShouldReturnCorrectPageContent_WhenDataExists() throws Exception {

        FilmApiResponse apiResponse = new FilmApiResponse("ok", allFilmsList);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 2);

        Page<FilmsResult> resultPage = filmService.getAllFilms(pageable);

        assertNotNull(resultPage, "La página no debe ser nula");
        assertEquals(3, resultPage.getTotalElements(), "Debe haber 3 elementos totales");
        assertEquals(2, resultPage.getSize(), "El tamaño de la página debe ser 2");
        assertEquals(2, resultPage.getContent().size(), "El contenido debe tener 2 elementos");
        assertTrue(resultPage.getContent().contains(film1), "Debe contener la película 1");
        assertTrue(resultPage.getContent().contains(film2), "Debe contener la película 2");
        assertFalse(resultPage.getContent().contains(film3), "No debe contener la película 3");
    }

    @Test
    void getAllFilms_ShouldReturnEmptyPage_WhenApiReturnsNoData() throws Exception {
        FilmApiResponse apiResponse = new FilmApiResponse("ok", Collections.emptyList());
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(0, 10);


        Page<FilmsResult> resultPage = filmService.getAllFilms(pageable);


        assertTrue(resultPage.isEmpty(), "La página debe estar vacía");
        assertEquals(0, resultPage.getTotalElements(), "El total debe ser 0");
    }

    @Test
    void getAllFilms_ShouldHandleOutOfBoundsPageRequest() throws Exception {
        FilmApiResponse apiResponse = new FilmApiResponse("ok", allFilmsList);
        String jsonResponse = gson.toJson(apiResponse);

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        Pageable pageable = PageRequest.of(10, 2);


        Page<FilmsResult> resultPage = filmService.getAllFilms(pageable);


        assertTrue(resultPage.isEmpty(), "La página debe estar vacía si está fuera de límites");
        assertEquals(3, resultPage.getTotalElements(), "El total de elementos debe seguir siendo 3");
    }

    @Test
    void getById_ShouldReturnSingleFilmResult_WhenValidUidIsProvided() throws Exception {

        String testUid = "1";
        FilmsResult film1 = new FilmsResult(testUid, "A New Hope", null);

        FilmApiResponseFilter singleApiResponse = new FilmApiResponseFilter("ok", film1);


        String jsonResponse = gson.toJson(singleApiResponse);


        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        FilmsResult result = filmService.getById(testUid);

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(testUid, result.uid(), "El UID de la película debe coincidir");
        assertEquals("A New Hope", result.description(), "El nombre/descripción de la película debe coincidir");
    }

    @Test
    void getById_ShouldThrowException_WhenApiCallFails() throws Exception {

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException("Simulated Network Error"));

        assertThrows(IOException.class, () -> {
            filmService.getById("1");
        }, "Debería lanzar IOException al fallar la red");
    }
}
