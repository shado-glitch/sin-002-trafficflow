package co.wethinkcode.trafficflow;

import io.javalin.Javalin;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngestionServiceAppTest {

    @Test
    void shouldReturnHealthOk() throws Exception {

        Javalin app =
                Javalin.create().start(7025);

        app.get(
                "/health",
                ctx -> ctx.result("OK")
        );

        HttpClient client =
                HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(
                                "http://localhost:7025/health"
                        ))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.body());

        app.stop();
    }

    @Test
    void shouldReturnIntersections() throws Exception {

        Javalin app =
                Javalin.create().start(7026);

        IntersectionRepository repository =
                IntersectionCsvReader.load();

        app.get(
                "/intersections",
                ctx -> ctx.json(repository.findAll())
        );

        HttpClient client =
                HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(
                                "http://localhost:7026/intersections"
                        ))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        assertEquals(200, response.statusCode());

        app.stop();
    }
}