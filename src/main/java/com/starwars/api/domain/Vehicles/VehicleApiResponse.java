package com.starwars.api.domain.Vehicles;

import java.util.List;

public record VehicleApiResponse(String message, List<VehicleResult> result) {
}
