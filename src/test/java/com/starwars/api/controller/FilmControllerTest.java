package com.starwars.api.controller;

import com.starwars.api.domain.Films.FilmsResult;
import com.starwars.api.exception.ResourceNotFoundException;
import com.starwars.api.service.FilmService;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FilmService filmService;

    private FilmsResult film1 = new FilmsResult("1", "A New Hope", null);
    private FilmsResult film2 = new FilmsResult("2", "The Empire Strikes Back", null);


    @Test
    void getAllFilms_ShouldReturnPageOfFilms_AndHttpStatusOk() throws Exception {
        List<FilmsResult> filmList = Arrays.asList(film1, film2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<FilmsResult> mockedPage = new PageImpl<>(filmList, pageable, 2);

        when(filmService.getAllFilms(any(Pageable.class))).thenReturn(mockedPage);


        mockMvc.perform(get("/api/films/")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getById_ShouldReturnSingleFilm_AndHttpStatusOk() throws Exception {
        String testUid = "1";
        when(filmService.getById(eq(testUid))).thenReturn(film1);
        mockMvc.perform(get("/api/films/{uid}", testUid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"uid\":\"1\", \"description\":\"A New Hope\"}"));
    }

    @Test
    void getById_ShouldReturnNotFound_WhenServiceThrowsResourceNotFoundException() throws Exception {
        when(filmService.getById(any(String.class)))
                .thenThrow(new ResourceNotFoundException("Película con UID 999 no encontrada."));

        mockMvc.perform(get("/api/films/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
