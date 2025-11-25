package features.Starships;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class StarshipsTest {
    @Test
    Karate testStarships() {
        return Karate.run("classpath:features/Starships/starships.feature");
    }
}
