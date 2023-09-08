package hr.algebra.dal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//https://www.baeldung.com/java-bean-validation-not-null-empty-blank

@Entity
@Table(name="video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @NotEmpty(message = "Name is required")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Description is required")
    @Column(name = "description")
    private String description;

    @Min(value = 1, message = "Genre id must be more than 0")
    @Column(name = "genreId")
    private int genreId;

    @Min(value = 1, message = "Time must be more than 0 seconds")
    @Column(name = "totalSeconds")
    private int totalSeconds;

    @NotEmpty(message = "Streaming URL may not be empty")
    @URL(message = "The URL is not valid")
    @Column(name = "streamingUrl")
    private String streamingUrl;

    @Min(value = 1, message = "Image id must be more than 0")
    @Column(name = "imageId")
    private int imageId;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "VideoTag",
            joinColumns = { @JoinColumn(name = "video_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private Set<Tag> tags = new HashSet<>();

    public Video() {
    }

    public Video(LocalDateTime createdAt, String name, String description, int genreId, int totalSeconds, String streamingUrl, int imageId, Set<Tag> tags) {
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.description = description;
        this.genreId = genreId;
        this.totalSeconds = totalSeconds;
        this.streamingUrl = streamingUrl;
        this.imageId = imageId;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public String getStreamingUrl() {
        return streamingUrl;
    }

    public void setStreamingUrl(String streamingUrl) {
        this.streamingUrl = streamingUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
