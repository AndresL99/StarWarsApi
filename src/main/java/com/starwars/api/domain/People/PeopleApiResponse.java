package com.starwars.api.domain.People;

import java.util.List;

public record PeopleApiResponse(String message, List<PeopleResult> result) {
}
