package com.starwars.api.domain.Starships;

import java.util.List;

public record StarshipApiResponse(String message, List<StarshipResult> result) {
}
