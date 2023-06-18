package action.in.blog;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeoutTests {

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void throw_connection_timeout() {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(1))
                .build();
        CompletableFuture.runAsync(() -> {
            restTemplate.getForObject("http://localhost:8080/foo", String.class);
        });


        RuntimeException throwable = assertThrows(RuntimeException.class, () -> {
            sleep(1000);
            restTemplate.getForObject("http://localhost:8080/foo", String.class);
        });


        assertThat(throwable instanceof ResourceAccessException, equalTo(true));
        assertThat(throwable.getCause() instanceof SocketTimeoutException, equalTo(true));
        assertThat(throwable.getMessage(), equalTo("I/O error on GET request for \"http://localhost:8080/foo\": Connect timed out"));
    }

    @Test
    void throw_socket_timeout() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(1000)
                .build();
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpUriRequest httpUriRequest = new HttpGet(URI.create("http://localhost:8080/foo"));


        CompletableFuture.runAsync(() -> {
            try {
                httpClient.execute(httpUriRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        SocketTimeoutException throwable = assertThrows(SocketTimeoutException.class, () -> {
            sleep(1000);
            httpClient.execute(httpUriRequest);
        });


        assertThat(throwable.getMessage(), equalTo("Read timed out"));
    }

    @Test
    void throw_read_timeout() {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .setReadTimeout(Duration.ofSeconds(1))
                .build();
        RuntimeException throwable = assertThrows(RuntimeException.class, () -> {
            restTemplate.getForObject("http://localhost:8080/foo", String.class);
        });


        assertThat(throwable instanceof ResourceAccessException, equalTo(true));
        assertThat(throwable.getCause() instanceof SocketTimeoutException, equalTo(true));
        assertThat(throwable.getMessage(), equalTo("I/O error on GET request for \"http://localhost:8080/foo\": Read timed out"));
    }
}
