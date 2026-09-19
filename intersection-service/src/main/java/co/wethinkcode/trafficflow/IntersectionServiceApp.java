package co.wethinkcode.trafficflow;

import io.javalin.Javalin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class IntersectionServiceApp {

    private final IntersectionRepository repository = new IntersectionRepository();

    public void addIntersection(Intersection intersection) {
        repository.add(intersection);
    }

    public Intersection findIntersection(String id) {
        return repository.findById(id);
    }

    public List<Intersection> findByDistrict(String district) {
        return repository.findByDistrict(district);
    }

    public List<Intersection> findAll() {
        return repository.findAll();
    }

    /**
     * Loads the cleaned intersections from ingestion-service.
     */

    private String ingestion_uri ="http://localhost:7020/intersections";

    public void loadFromIngestion() {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ingestion_uri))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {
                throw new RuntimeException("Ingestion service returned HTTP " + response.statusCode());
            }

            ObjectMapper mapper = new ObjectMapper();

            List<Intersection> intersections =
                    mapper.readValue(
                            response.body(),
                            new TypeReference<List<Intersection>>() {}
                    );

            repository.clear();

            for (Intersection intersection : intersections) {
                repository.add(intersection);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not load intersections from ingestion service",
                    e
            );
        }
    }
    
    public static void main(String[] args) {

        IntersectionServiceApp service = new IntersectionServiceApp();
        Javalin app = Javalin.create().start(7021);

        app.get("/health", ctx -> ctx.result("OK"));

        service.loadFromIngestion();
        // TODO (Validates intersection/district names (source of truth).)
        // Add domain endpoints for intersection-service here.
         /*
         * GET /intersections
         *
         * Return every known intersection.
         */
        app.get("/intersections", ctx -> {
            ctx.json(service.findAll());
        });

        /*
         * GET /intersections/{id}
         *
         * Used by routing-service.
         */
        app.get("/intersections/{id}", ctx -> {

            String id = ctx.pathParam("id");

            Intersection intersection = service.findIntersection(id);

            if (intersection == null) {
                ctx.status(404);
                ctx.json(Map.of(
                        "error",
                        "Unknown intersection: " + id
                ));
                return;
            }

            ctx.json(intersection);
        });

        /*
         * GET /intersections/district/{district}
         */
        app.get("/intersections/district/{district}", ctx -> {

            String district = ctx.pathParam("district");

            List<Intersection> intersections = service.findByDistrict(district);

            if (intersections.isEmpty()) {
                ctx.status(404);
                ctx.json(Map.of(
                        "error",
                        "Unknown district: " + district
                ));
                return;
            }

            ctx.json(intersections);
        });
    }
    
}

// MQ TODO: publishes a periodic heartbeat to ActiveMQ queue MqConfig.HEARTBEAT_QUEUE at
// MqConfig.BROKER_URL (see co.wethinkcode.trafficflow.mq.MqConfig), consumed by intersection-watchdog.
