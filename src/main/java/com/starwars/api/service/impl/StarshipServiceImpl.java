package com.starwars.api.service.impl;

import com.google.gson.Gson;
import com.starwars.api.domain.Starships.StarshipApiResponse;
import com.starwars.api.domain.Starships.StarshipApiResponseFilter;
import com.starwars.api.domain.Starships.StarshipResult;
import com.starwars.api.domain.Starships.Starships;
import com.starwars.api.service.StarshipService;
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
public class StarshipServiceImpl implements StarshipService {

    private static final String API_URL = "https://www.swapi.tech/api/starships";

    private HttpClient httpClient;
    private Gson gson;

    public StarshipServiceImpl(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Override
    public Page<StarshipResult> getAllStarships(Pageable pageable) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        StarshipApiResponse starshipApiResponse = gson.fromJson(response.body(),StarshipApiResponse.class);

        List<StarshipResult>starshipResults = (starshipApiResponse != null && starshipApiResponse.result() != null)
                ? starshipApiResponse.result()
                :new ArrayList<>();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<StarshipResult>pageContent;

        if (starshipResults.size() < startItem) {
            pageContent = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, starshipResults.size());
            pageContent = starshipResults.subList(startItem, toIndex);
        }
        return new PageImpl<>(pageContent, pageable, starshipResults.size());
    }

    @Override
    public StarshipResult getById(String uid) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + uid))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        StarshipApiResponseFilter starshipApiResponse = gson.fromJson(response.body(),StarshipApiResponseFilter.class);

        return starshipApiResponse.result();
    }

    @Override
    public StarshipResult getByName(String name) {
        return null;
    }
}
