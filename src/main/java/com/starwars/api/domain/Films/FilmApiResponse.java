package com.starwars.api.domain.Films;

import java.util.List;

public record FilmApiResponse(String message, List<FilmsResult>result) {
}
