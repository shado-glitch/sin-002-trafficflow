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

    public static String cleanSignalType(String value) {

        if (value == null) {
            return null;
        }

        String cleaned =value.trim().replaceAll("\\s+", " ").toLowerCase();

        if (cleaned.isEmpty()) {
            return null;
        }

        if (isMissingValue(cleaned)) {
            return null;
        }

        return cleaned;
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

    private static boolean isMissingValue(String value) {

        return value.equals("n/a")|| value.equals("tbd") || value.equals("unknown") || value.equals("-") || value.equals("nan");
    }

    public static Boolean cleanBoolean(String value) {

    if (value == null) {
        return null;
    }

    String cleaned = value.trim().toLowerCase();

    switch (cleaned) {

        case "y":
        case "yes":
        case "1":
        case "true":
            return true;

        case "n":
        case "no":
        case "0":
        case "false":
            return false;

        default:
            return null;
        }
    }
}