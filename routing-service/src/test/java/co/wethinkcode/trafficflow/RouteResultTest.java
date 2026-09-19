package co.wethinkcode.trafficflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteResultTest {

    @Test
    void routeResultStoresCalculatedValues() {

        RouteResult result =
                new RouteResult(
                        "INT-1001",
                        4,
                        18
                );

        assertEquals(
                "INT-1001",
                result.getIntersectionId()
        );

        assertEquals(
                4,
                result.getCongestionLevel()
        );

        assertEquals(
                18,
                result.getEstimatedTravelTimeMinutes()
        );
    }
}