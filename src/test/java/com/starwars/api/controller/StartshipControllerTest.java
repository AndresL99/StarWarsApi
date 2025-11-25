package com.starwars.api.controller;

import com.starwars.api.domain.Starships.StarshipResult;
import com.starwars.api.exception.ResourceNotFoundException;
import com.starwars.api.service.StarshipService;
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

@WebMvcTest(controllers = StarshipController.class)
public class StartshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StarshipService starshipService;

    private StarshipResult startfighter = new StarshipResult("15","Startfighter",null);
    private StarshipResult startDestroyer = new StarshipResult("34","Star Destroyer", null);

    @Test
    void getAllStarships_ShouldReturnPageOfStarships_AndHttpStatusOk() throws Exception {
        List<StarshipResult> starshipResults = Arrays.asList(startfighter,startDestroyer);
        Pageable pageable = PageRequest.of(0, 10);
        Page<StarshipResult> mockedPage = new PageImpl<>(starshipResults, pageable, 2);

        when(starshipService.getAllStarships(any(Pageable.class))).thenReturn(mockedPage);


        mockMvc.perform(get("/api/starships/")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_ShouldReturnSingleStarships_AndHttpStatusOk() throws Exception {
        String testUid = "34";
        when(starshipService.getById(eq(testUid))).thenReturn(startDestroyer);
        mockMvc.perform(get("/api/starships/{uid}", testUid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"uid\":\"34\", \"description\":\"Star Destroyer\"}"));
    }

    @Test
    void getById_ShouldReturnNotFound_WhenServiceThrowsResourceNotFoundException() throws Exception {
        when(starshipService.getById(any(String.class)))
                .thenThrow(new ResourceNotFoundException("Starship con UID 999 no encontrada."));

        mockMvc.perform(get("/api/startships/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
