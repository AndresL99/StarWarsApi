package com.starwars.api.domain.People;


import java.util.List;

public record People(String name, String birth_year, String eye_color, String gender, String hair_color, String height,
                     String mass, String skin_color, String homeworld, List<String> films, List<String>species, List<String>starships,
                     List<String>vehicles, String url, String created, String edited) {
}
