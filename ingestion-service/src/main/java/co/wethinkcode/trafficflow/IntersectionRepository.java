package co.wethinkcode.trafficflow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IntersectionRepository {

    private final Map<String, Intersection> intersections =
            new LinkedHashMap<>();

    public void save(Intersection intersection) {

        intersections.put(
                intersection.getId(),
                intersection
        );
    }

    public List<Intersection> findAll() {

        return new ArrayList<>(
                intersections.values()
        );
    }
}