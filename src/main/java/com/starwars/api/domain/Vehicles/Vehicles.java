package com.starwars.api.domain.Vehicles;


import java.util.List;

public record Vehicles(String name, String model, String vehicle_class, String manufacturer, String length, String cost_in_credits,
                       String crew, String passengers, String max_atmosphering_speed, String cargo_capacity, String consumables,
                       List<String>films, List<String>pilots, String url, String created, String edited) {
}
