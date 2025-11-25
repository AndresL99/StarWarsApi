package com.starwars.api.service.impl;

import com.google.gson.Gson;
import com.starwars.api.domain.People.People;
import com.starwars.api.domain.People.PeopleApiResponse;
import com.starwars.api.domain.People.PeopleApiResponseFilter;
import com.starwars.api.domain.People.PeopleResult;
import com.starwars.api.service.PeopleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PeopleServiceImpl implements PeopleService {

    private static final String API_URL = "https://www.swapi.tech/api/people";
    private final HttpClient httpClient;
    private Gson gson = new Gson();

    public PeopleServiceImpl(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Override
    public Page<PeopleResult> getAllPeople(Pageable pageable) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        PeopleApiResponse peopleApiResponse = gson.fromJson(response.body(),PeopleApiResponse.class);
        List<PeopleResult>peopleResults = (peopleApiResponse != null && peopleApiResponse.result() != null)
                ?peopleApiResponse.result()
                :new ArrayList<>();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<PeopleResult>pageContent;

        if(peopleResults.size() < startItem)
        {
            pageContent = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startItem + pageSize, peopleResults.size());
            pageContent = peopleResults.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageContent,pageable,peopleResults.size());
    }

    @Override
    public PeopleResult getById(String uid) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + uid))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        PeopleApiResponseFilter peopleApiResponse = gson.fromJson(response.body(),PeopleApiResponseFilter.class);

        return peopleApiResponse.result();
    }

    @Override
    public PeopleResult getByName(String name) {
        return null;
    }
}
