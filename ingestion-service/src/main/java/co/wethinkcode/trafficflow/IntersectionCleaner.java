package co.wethinkcode.trafficflow;

public class IntersectionCleaner {

    public static String cleanId(String value) {

        if (value == null) {
            return null;
        }

        String cleaned = value.trim();

        if (cleaned.isEmpty()) {
            return null;
        }

        return cleaned.toUpperCase();
    }

    public static String cleanDistrict(String value) {

        if (value == null) {
            return null;
        }

        String cleaned =
                value.trim().replaceAll("\\s+", " ");

        if (cleaned.isEmpty()) {
            return null;
        }

        return titleCase(cleaned);
    }

    private static String titleCase(String value) {

        String[] words = value.toLowerCase().split(" ");

        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (result.length() > 0) {
                result.append(" ");
                }

            result.append(Character.toUpperCase(word.charAt(0)));

            if (word.length() > 1) {
                result.append(word.substring(1));
                }
            }

        return result.toString();
    }
}