package com.starwars.api.controller;

import com.starwars.api.domain.Starships.StarshipResult;
import com.starwars.api.service.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/starships/")
public class StarshipController {

    @Autowired
    private StarshipService starshipService;

    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @GetMapping
    public ResponseEntity<Page<StarshipResult>>getAllStarship(@PageableDefault(size = 2, page = 0) Pageable pageable) throws IOException, InterruptedException {
        //verifyAuth(authentication);
        Page<StarshipResult>starshipResultPage = starshipService.getAllStarships(pageable);
        return ResponseEntity.ok(starshipResultPage);
    }

    @GetMapping(value = "/{uid}")
    public ResponseEntity<StarshipResult>getById(@PathVariable String uid) throws IOException, InterruptedException {
        com.starwars.api.domain.Starships.StarshipResult starshipResult = starshipService.getById(uid);
        return ResponseEntity.ok(starshipResult);
    }

    /*private void verifyAuth(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getUserGranted())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }*/
}
