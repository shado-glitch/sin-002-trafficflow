package co.wethinkcode.trafficflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RoutingService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final String intersectionServiceUrl;
    private final String congestionServiceUrl;

    public RoutingService() {

        this(HttpClient.newHttpClient(),
        new ObjectMapper(),
        "http://localhost:7021",
        "http://localhost:7022");
    }

    public RoutingService(HttpClient httpClient,ObjectMapper objectMapper,String intersectionServiceUrl,String congestionServiceUrl) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.intersectionServiceUrl = intersectionServiceUrl;
        this.congestionServiceUrl = congestionServiceUrl;
    }

    public RouteResult calculateRoute(String intersectionId) throws Exception {

        /*
         * STEP 1
         *
         * Ask intersection-service whether
         * this intersection exists.
         */
        HttpResponse<String> intersectionResponse = get(intersectionServiceUrl + "/intersections/"+ intersectionId);

        if (intersectionResponse.statusCode() == 404) {
            throw new UnknownIntersectionException("Unknown intersection: "+ intersectionId);
        }

        if (intersectionResponse.statusCode() != 200) {
            throw new DependencyException("Intersection service returned HTTP "+ intersectionResponse.statusCode());
        }

        /*
         * STEP 2
         *
         * Ask congestion-service for the
         * current city-wide congestion level.
         */
        HttpResponse<String> congestionResponse = get(congestionServiceUrl + "/congestion");

        if (congestionResponse.statusCode() != 200) {

            throw new DependencyException("Congestion service returned HTTP "+ congestionResponse.statusCode());
        }

        /*
         * STEP 3
         *
         * Extract congestion level.
         */
        JsonNode congestionJson = objectMapper.readTree(congestionResponse.body());
        int congestionLevel = congestionJson.get("level").asInt();

        /*
         * STEP 4
         *
         * Calculate travel time.
         *
         * Base journey = 10 minutes.
         *
         * Every congestion level adds
         * 2 minutes.
         *
         * Therefore:
         *
         * level 0 -> 10 minutes
         * level 1 -> 12 minutes
         * level 4 -> 18 minutes
         * level 8 -> 26 minutes
         */
        int baseTravelTime = 10;

        int estimatedTravelTime = baseTravelTime + (congestionLevel * 2);

        return new RouteResult(intersectionId,congestionLevel,estimatedTravelTime);
    }

    private HttpResponse<String> get(String url) throws IOException, InterruptedException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        try {

            return httpClient.send(request,HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new DependencyException("Could not reach dependency: "+ url,e);
        }
    }
}