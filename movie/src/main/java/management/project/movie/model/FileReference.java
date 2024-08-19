package management.project.movie.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "file_references")
public class FileReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bucket_name", nullable = false)
    private String bucketName;

    @Column(name = "object_name", nullable = false)
    private String objectName;

    @Column(name = "file_url")
    private String fileUrl;
}

