package management.project.movie.controller;

import management.project.movie.service.MinIOService;
import management.project.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final MinIOService minioService;

    @Autowired
    public MovieService movieService;

    @Autowired
    public FileController(MinIOService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String bucketName = "movie-management";
            String objectName = file.getOriginalFilename();
            String filePath = "C:\\Users\\brent\\OneDrive\\Desktop\\movies.csv";

            minioService.uploadFile(bucketName, objectName, filePath);

            return ResponseEntity.ok("File uploaded successfully in MinIO!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public String downloadFileToLocal(
            @RequestParam String bucketName,
            @RequestParam String objectName,
            @RequestParam String localFilePath) {
        try {
            minioService.downloadFileToLocal(bucketName, objectName, localFilePath);
            return "File downloaded successfully and stored in: " + localFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to download file: " + e.getMessage();
        }
    }
}