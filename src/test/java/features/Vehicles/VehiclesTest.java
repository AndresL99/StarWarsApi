package features.Vehicles;


import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class VehiclesTest {

    @Test
    Karate testVehicles() {
        return Karate.run("classpath:features/Vehicles/vehicles.feature").relativeTo(getClass());
    }
}
