package com.starwars.api.controller;

import com.starwars.api.domain.People.PeopleResult;
import com.starwars.api.exception.ResourceNotFoundException;
import com.starwars.api.service.PeopleService;
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

@WebMvcTest(controllers = PeopleController.class)
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PeopleService peopleService;

    private PeopleResult darthVader = new PeopleResult("22","Darth Vader", null);
    private PeopleResult lukeSkywalker = new PeopleResult("12","Luke Skywalker", null);


    @Test
    void getAllPeople_ShouldReturnPageOfPeople_AndHttpStatusOk() throws Exception {
        List<PeopleResult> peopleResults = Arrays.asList(darthVader,lukeSkywalker);
        Pageable pageable = PageRequest.of(0, 10);
        Page<PeopleResult> mockedPage = new PageImpl<>(peopleResults, pageable, 2);

        when(peopleService.getAllPeople(any(Pageable.class))).thenReturn(mockedPage);


        mockMvc.perform(get("/api/people/")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_ShouldReturnSinglePeople_AndHttpStatusOk() throws Exception {
        String testUid = "22";
        when(peopleService.getById(eq(testUid))).thenReturn(darthVader);
        mockMvc.perform(get("/api/people/{uid}", testUid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"uid\":\"22\", \"description\":\"Darth Vader\"}"));
    }

    @Test
    void getById_ShouldReturnNotFound_WhenServiceThrowsResourceNotFoundException() throws Exception {
        when(peopleService.getById(any(String.class)))
                .thenThrow(new ResourceNotFoundException("Personaje con UID 999 no encontrado."));

        mockMvc.perform(get("/api/people/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
