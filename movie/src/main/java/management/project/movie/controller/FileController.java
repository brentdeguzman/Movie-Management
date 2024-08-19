package management.project.movie.controller;

import management.project.movie.service.MinIOService;
import management.project.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${minio.bucket-name}")
    private String defaultBucketName;

    @Value("${file.path}")
    private String directoryPath;

    @Autowired
    public MovieService movieService;

    @Autowired
    public FileController(MinIOService minioService) {
        this.minioService = minioService;
    }

    //export csv from db,
    @GetMapping("/minio/download")
    public String downloadFileToLocal(
            @RequestParam String bucketName,
            @RequestParam String objectName,
            @RequestParam String localFilePath) {
        try {
            minioService.downloadFile(bucketName, objectName, localFilePath);
            return "File downloaded successfully and stored in: " + localFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to download file: " + e.getMessage();
        }
    }

    //http, rest api
}