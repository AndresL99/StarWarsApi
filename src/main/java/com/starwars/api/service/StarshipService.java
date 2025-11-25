package com.starwars.api.service;

import com.starwars.api.domain.Starships.StarshipResult;
import com.starwars.api.domain.Starships.Starships;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface StarshipService {

    Page<StarshipResult> getAllStarships(Pageable pageable) throws IOException, InterruptedException;
    StarshipResult getById(String uid) throws IOException, InterruptedException;
    StarshipResult getByName(String name);
}
