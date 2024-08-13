package management.project.movie.service;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.List;

public class CSVUpdate {

    public List<String[]> readCsvFromResources() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies.csv");
        if (inputStream == null) {
            throw new IOException("File not found in resources: movie.csv");
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            System.out.println(reader.toString()); // Skip header
            return reader.readAll(); // Read all rows into a List of String arrays
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    // Example update method
    public void updateData(List<String[]> data) {
        for (String[] row : data) {
            if ("SomeCondition".equals(row[0])) {
                row[1] = "Blah";
            }
        }
    }

    public void writeCsv(List<String[]> data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter("updated_movies.csv"))) {
            writer.writeAll(data);
        }
    }

    public static void main(String[] args) {
        CSVUpdate updater = new CSVUpdate();
        try {
            List<String[]> data = updater.readCsvFromResources();
            updater.updateData(data);
            updater.writeCsv(data);
            System.out.println("CSV file updated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
