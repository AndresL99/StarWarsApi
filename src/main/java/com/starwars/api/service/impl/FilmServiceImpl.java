package com.starwars.api.service.impl;

import com.google.gson.Gson;
import com.starwars.api.domain.Films.FilmApiResponse;
import com.starwars.api.domain.Films.FilmApiResponseFilter;
import com.starwars.api.domain.Films.Films;
import com.starwars.api.domain.Films.FilmsResult;
import com.starwars.api.service.FilmService;
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
public class FilmServiceImpl implements FilmService {

    private static final String API_URL = "https://www.swapi.tech/api/films";

    private final HttpClient httpClient;
    private Gson gson = new Gson();

    public FilmServiceImpl(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Override
    public Page<FilmsResult> getAllFilms(Pageable pageable) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        FilmApiResponse filmApiResponse = gson.fromJson(response.body(), FilmApiResponse.class);

        List<FilmsResult> filmsResults = (filmApiResponse != null && filmApiResponse.result() != null)
                ? filmApiResponse.result()
                : new ArrayList<>();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<FilmsResult> pageContent;

        if (filmsResults.size() < startItem) {
            pageContent = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, filmsResults.size());
            pageContent = filmsResults.subList(startItem, toIndex);
        }
        return new PageImpl<>(pageContent, pageable, filmsResults.size());
    }

    @Override
    public FilmsResult getById(String uid) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL +"/"+uid))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        FilmApiResponseFilter filmApiResponse = gson.fromJson(response.body(), FilmApiResponseFilter.class);

        return filmApiResponse.result();
    }

    @Override
    public FilmsResult getByName(String name) {
        return null;
    }
}
