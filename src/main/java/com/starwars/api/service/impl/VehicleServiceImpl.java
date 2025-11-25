package com.starwars.api.service.impl;

import com.google.gson.Gson;
import com.starwars.api.domain.Vehicles.VehicleApiResponse;
import com.starwars.api.domain.Vehicles.VehicleApiResponseFilter;
import com.starwars.api.domain.Vehicles.VehicleResult;
import com.starwars.api.domain.Vehicles.Vehicles;
import com.starwars.api.service.VehicleService;
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
public class VehicleServiceImpl implements VehicleService {

    private static final String API_URL = "https://www.swapi.tech/api/vehicles";

    private HttpClient httpClient;
    private Gson gson;

    public VehicleServiceImpl(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Override
    public Page<VehicleResult> getAllVehicles(Pageable pageable) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        VehicleApiResponse vehicleApiResponse = gson.fromJson(response.body(),VehicleApiResponse.class);

        List<VehicleResult>vehicleResults = (vehicleApiResponse != null && vehicleApiResponse.result() != null)
                ? vehicleApiResponse.result()
                : new ArrayList<>();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<VehicleResult>pageContent;

        if (vehicleResults.size() < startItem) {
            pageContent = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, vehicleResults.size());
            pageContent = vehicleResults.subList(startItem, toIndex);
        }
        return new PageImpl<>(pageContent, pageable, vehicleResults.size());
    }

    @Override
    public VehicleResult getById(String uid) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + uid))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        VehicleApiResponseFilter vehicleApiResponse = gson.fromJson(response.body(),VehicleApiResponseFilter.class);
        return vehicleApiResponse.result();
    }

    @Override
    public VehicleResult getByName(String name) {
        return null;
    }
}
