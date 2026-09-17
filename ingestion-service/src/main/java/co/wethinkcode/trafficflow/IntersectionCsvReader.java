package co.wethinkcode.trafficflow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IntersectionCsvReader {

    public static IntersectionRepository load() {

        // Create a repository where we will store
        // the cleaned intersections.
        IntersectionRepository repository =
                new IntersectionRepository();

        // Find the CSV file inside src/main/resources.
        InputStream inputStream =
                IntersectionCsvReader.class
                        .getClassLoader()
                        .getResourceAsStream(
                                "intersections-legacy.csv"
                        );

        // Check that the file was actually found.
        if (inputStream == null) {

            throw new RuntimeException(
                    "Could not find intersections-legacy.csv"
            );
        }

        // BufferedReader allows us to read the file
        // one line at a time.
        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(inputStream)
                        )
        ) {

            // The first line contains column names,
            // so we don't want to treat it as an intersection.
            reader.readLine();

            String line;

            // Keep reading until there are no more lines.
            while ((line = reader.readLine()) != null) {

                // Split the CSV row into columns.
                //
                // The -1 is important because it tells Java
                // to keep empty columns.
                String[] columns =
                        line.split(",", -1);

                // We need four columns:
                //
                // 0 = ID
                // 1 = District
                // 2 = Signal type
                // 3 = Active flag
                //
                // If the row doesn't have four columns,
                // ignore that row.
                if (columns.length < 4) {
                    continue;
                }

                // Clean the ID.
                String id =
                        IntersectionCleaner.cleanId(
                                columns[0]
                        );

                // If there is no ID, we cannot reliably
                // identify the intersection.
                if (id == null) {
                    continue;
                }

                // Clean the district.
                String district =
                        IntersectionCleaner.cleanDistrict(
                                columns[1]
                        );

                // Clean the signal type.
                String signalType =
                        IntersectionCleaner.cleanSignalType(
                                columns[2]
                        );

                // Clean the active flag.
                Boolean active =
                        IntersectionCleaner.cleanBoolean(
                                columns[3]
                        );

                // Create an Intersection object
                // using the cleaned values.
                Intersection intersection =
                        new Intersection(
                                id,
                                district,
                                signalType,
                                active
                        );

                // Store the intersection.
                //
                // If the ID already exists, the repository
                // will replace the existing record.
                repository.save(intersection);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not read intersections CSV",
                    e
            );
        }

        // Return all the intersections we loaded.
        return repository;
    }
}
