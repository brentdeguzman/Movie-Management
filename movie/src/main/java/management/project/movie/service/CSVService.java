//package management.project.movie.service;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import management.project.movie.dto.MovieRequest;
//import management.project.movie.model.Actor;
//import management.project.movie.model.Genre;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//public class CSVService {
//
//    public List<MovieRequest> readCsv(String filePath) throws IOException, CsvValidationException {
//        List<MovieRequest> movieRequests = new ArrayList<>();
//
//        File file = new File(filePath);
//        if (!file.exists()) {
//            System.out.println("File not found: " + filePath);
//            return movieRequests; // Return empty list to avoid null
//        }
//
//        try (CSVReader reader = new CSVReader(new FileReader(file))) {
//            String[] line;
//            while ((line = reader.readNext()) != null) {
//                if (line.length == 0) {
//                    System.out.println("Empty line in CSV");
//                    continue;
//                }
//
//                // Process and create MovieRequest
//                MovieRequest movieRequest = new MovieRequest();
//                movieRequest.setName(line[0]);
//                movieRequest.setReleaseYear(Integer.parseInt(line[1]));
//                //movieRequest.setDirectorName(line[2]);
//                //movieRequest.setGenres(Arrays.asList(line[3].split(";")));
//                movieRequest.setActors(parseActors(line[4]));
//                // movieRequest.setRating(Integer.parseInt(line[5]));
//
//                movieRequests.add(movieRequest);
//            }
//        }
//
//        System.out.println("Parsed " + movieRequests.size() + " movies");
//        return movieRequests;
//    }
//
//
//    private List<Genre> parseGenres(String genreString) {
//        List<Genre> genres = new ArrayList<>();
//        String[] genreArray = genreString.split(";");
//        for (String genreName : genreArray) {
//            Genre genre = new Genre();
//            genre.setName(genreName.trim());
//            genres.add(genre);
//        }
//        return genres;
//    }
//
//    private List<Actor> parseActors(String actorString) {
//        List<Actor> actors = new ArrayList<>();
//        String[] actorArray = actorString.split(";");
//        for (String actorName : actorArray) {
//            Actor actor = new Actor();
//            String[] actorDetails = actorName.split(":");
//            actor.setName(actorDetails[0].trim());
//            actor.setRole(actorDetails[1].trim());
//            actors.add(actor);
//        }
//        return actors;
//    }
//
//}
//
