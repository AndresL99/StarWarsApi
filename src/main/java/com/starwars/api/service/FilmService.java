package com.starwars.api.service;



import com.starwars.api.domain.Films.Films;
import com.starwars.api.domain.Films.FilmsResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface FilmService {

    Page<FilmsResult> getAllFilms(Pageable pageable) throws IOException, InterruptedException;
    FilmsResult getById(String uid) throws IOException, InterruptedException;
    FilmsResult getByName(String name);

}
