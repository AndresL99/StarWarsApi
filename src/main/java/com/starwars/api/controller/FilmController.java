package com.starwars.api.controller;

import com.starwars.api.domain.Films.FilmsResult;
import com.starwars.api.domain.Users.Dto.UserDto;
import com.starwars.api.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/films/")
public class FilmController {

    @Autowired
    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('AUTHORIZED')")
    public ResponseEntity<Page<FilmsResult>> getAllFilms(Authentication authentication,@PageableDefault(size = 10, page = 0) Pageable pageable) throws IOException, InterruptedException {
        verifyAuth(authentication);
        Page<FilmsResult> filmsPage = filmService.getAllFilms(pageable);
        return ResponseEntity.ok(filmsPage);
    }

    @GetMapping(value = "/{uid}")
    public ResponseEntity<FilmsResult>getById(@PathVariable String uid) throws IOException, InterruptedException {
        FilmsResult filmsResults = filmService.getById(uid);
        return ResponseEntity.ok(filmsResults);
    }


    private void verifyAuth(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getIsAuthorized())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }
}
