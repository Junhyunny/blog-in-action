package cloud.in.action;

import cloud.in.action.domain.Health;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@FeignClient(name = "test-client", url = "http://localhost:8080")
interface TestClient {

    @GetMapping(path = "/health")
    List<Health> health();
}

@Slf4j
@SpringBootTest
class AServiceApplicationTests {

    @Autowired
    TestClient testClient;

    @Test
    void health_check() {

        List<Health> healthList = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            healthList.addAll(testClient.health());
        }

        Map<String, List<Health>> result = healthList.stream()
                .collect(
                        Collectors.groupingBy(
                                (health) -> String.format("%s:%s", health.getServiceName(), health.getPort())
                        )
                );

        result.entrySet()
                .stream()
                .forEach((entry) -> {
                    log.info(String.format("Response count from (%s) - %s", entry.getKey(), entry.getValue().size()));
                });
    }
}
