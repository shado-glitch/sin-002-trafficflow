package co.wethinkcode.trafficflow;

public class RouteResult {

    private final String intersectionId;
    private final int congestionLevel;
    private final int estimatedTravelTimeMinutes;

    public RouteResult(
            String intersectionId,
            int congestionLevel,
            int estimatedTravelTimeMinutes
    ) {

        this.intersectionId =
                intersectionId;

        this.congestionLevel =
                congestionLevel;

        this.estimatedTravelTimeMinutes =
                estimatedTravelTimeMinutes;
    }

    public String getIntersectionId() {
        return intersectionId;
    }

    public int getCongestionLevel() {
        return congestionLevel;
    }

    public int getEstimatedTravelTimeMinutes() {
        return estimatedTravelTimeMinutes;
    }
}