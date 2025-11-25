package features;

import com.intuit.karate.junit5.Karate;

import org.junit.jupiter.api.Test;

public class RunnerTest {

    @Test
    Karate runAllFeatures() {
        return Karate.run("classpath:features")
                .relativeTo(getClass());
    }
}
