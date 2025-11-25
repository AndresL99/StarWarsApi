package features.Films;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;
import org.junit.jupiter.api.TestFactory;

public class FilmsTest {

    @TestFactory
    Karate testFilms() {
        return Karate.run("films").relativeTo(getClass());
        }
}
