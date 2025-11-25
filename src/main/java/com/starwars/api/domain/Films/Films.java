package com.starwars.api.domain.Films;

import java.util.Date;
import java.util.List;

public record Films(String title, String episode_id, String opening_crawl, String director, String producer, Date release_date,
                    List<String>species, List<String>starships, List<String>vehicles, List<String>characters, List<String>planets,
                    String url, String created, String edited) {
}
