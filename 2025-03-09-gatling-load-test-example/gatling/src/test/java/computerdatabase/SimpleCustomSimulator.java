package computerdatabase;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SimpleCustomSimulator extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    FeederBuilder<String> feeder = csv("search.csv").random();

    ChainBuilder generateKey = exec(
            feed(feeder),
            http("Home")
                    .get("/target?username=${username}")
                    .check(
                            status().is(200)
                    )
    );

    ScenarioBuilder generateKeyScenario = scenario("GenerateKey").exec(generateKey);

    {
        setUp(
                generateKeyScenario.injectOpen(rampUsers(10).during(10))
        ).assertions(
                global().responseTime().max().lt(1000),
                global().successfulRequests().percent().gt(95.0)
        ).protocols(httpProtocol);
    }
}
