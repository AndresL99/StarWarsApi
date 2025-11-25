package features.People;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class PeopleTest {

    @Test
    Karate testPeople() {
        return Karate.run("classpath:features/People/people.feature");
    }
}
