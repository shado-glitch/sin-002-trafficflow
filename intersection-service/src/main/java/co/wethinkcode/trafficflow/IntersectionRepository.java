package co.wethinkcode.trafficflow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IntersectionRepository {

    private final Map<String, Intersection> intersections = new LinkedHashMap<>();

    public void add(Intersection intersection) {

        if (intersection == null || intersection.getId() == null) {
            return;
        }

        String id = intersection.getId().trim().toUpperCase();

        if (intersections.containsKey(id)) {
            System.out.println(
                    "Duplicate intersection detected: " + id
            );
        }

        intersections.put(id, intersection);
    }

    public Intersection findById(String id) {

        if (id == null) {
            return null;
        }

        String cleanedId = id.trim().toUpperCase();

        return intersections.get(cleanedId);
    }

    public List<Intersection> findByDistrict(String district) {

        List<Intersection> results = new ArrayList<>();

        if (district == null) {
            return results;
        }

        String cleanedDistrict = district.trim();

        for (Intersection intersection : intersections.values()) {

            if (intersection.getDistrict() != null
                    && intersection.getDistrict()
                    .trim()
                    .equalsIgnoreCase(cleanedDistrict)) {

                results.add(intersection);
            }
        }

        return results;
    }

    public List<Intersection> findAll() {
        return new ArrayList<>(intersections.values());
    }

    public void clear() {
        intersections.clear();
    }
}