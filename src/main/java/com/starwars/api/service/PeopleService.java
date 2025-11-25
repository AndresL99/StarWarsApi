package com.starwars.api.service;

import com.starwars.api.domain.People.People;
import com.starwars.api.domain.People.PeopleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface PeopleService {

    Page<PeopleResult> getAllPeople(Pageable pageable) throws IOException, InterruptedException;
    PeopleResult getById(String uid) throws IOException, InterruptedException;
    PeopleResult getByName(String name);
}
