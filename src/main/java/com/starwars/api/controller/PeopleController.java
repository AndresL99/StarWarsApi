package com.starwars.api.controller;

import com.starwars.api.domain.People.PeopleResult;
import com.starwars.api.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/people/")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PreAuthorize(value = "hasAnyAuthority('AUTHORIZED')")
    @GetMapping
    public ResponseEntity<Page<PeopleResult>>getAllPeople(@PageableDefault(size = 10, page = 0) Pageable pageable) throws IOException, InterruptedException {
        Page<PeopleResult>peopleResultPage = peopleService.getAllPeople(pageable);
        return ResponseEntity.ok(peopleResultPage);
    }

    @PreAuthorize(value = "hasAnyAuthority('AUTHORIZED')")
    @GetMapping(value = "/{uid}")
    public ResponseEntity<PeopleResult>getById(@PathVariable String uid) throws IOException, InterruptedException {
        com.starwars.api.domain.People.PeopleResult peopleResult = peopleService.getById(uid);
        return ResponseEntity.ok(peopleResult);
    }

}
