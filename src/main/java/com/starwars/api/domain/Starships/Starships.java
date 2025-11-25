package com.starwars.api.domain.Starships;


import java.util.List;

public record Starships(String name, String model, String starship_class, String manufacturer, String cost_in_credits, String length,
                        String crew, String passengers, String max_atmosphering_speed, String hyperdrive_rating, String MGLT, String cargo_capacity,
                        String consumables, List<String>films, List<String>pilots, String url, String created, String edited) {
}
