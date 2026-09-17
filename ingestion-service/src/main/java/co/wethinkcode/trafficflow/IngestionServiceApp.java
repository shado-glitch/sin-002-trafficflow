package co.wethinkcode.trafficflow;

import io.javalin.Javalin;

public class IngestionServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7020);
        IntersectionRepository repository =IntersectionCsvReader.load();

        app.get("/health", ctx -> ctx.result("OK"));
        app.get("/intersections",ctx -> ctx.json(repository.findAll()));

        // TODO: read and clean src/main/resources/intersections-legacy.csv (intersections, districts, signal types data —
        // trim whitespace, fix casing, normalize dates/booleans) and expose the
        // cleaned records here for the other services to consume.
    }
}
