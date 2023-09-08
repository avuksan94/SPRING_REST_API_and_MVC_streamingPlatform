package hr.algebra.bll.blModels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class VideoModel {

    private int id;
    private LocalDateTime createdAt;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @Min(value = 1, message = "Genre id must be more than 0")
    private int genreId;

    @Min(value = 1, message = "Time must be more than 0 seconds")
    private int totalSeconds;

    @NotEmpty(message = "Streaming URL may not be empty")
    @URL(message = "The URL is not valid")
    private String streamingUrl;

    @Min(value = 1, message = "Image id must be more than 0")
    private int imageId;
    private Set<TagModel> tags = new HashSet<>();

    public VideoModel() {
    }

    public VideoModel(LocalDateTime createdAt, String name, String description, int genreId, int totalSeconds, String streamingUrl, int imageId, Set<TagModel> tags) {
        this.createdAt = createdAt;
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

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genreId=" + genreId +
                ", totalSeconds=" + totalSeconds +
                ", streamingUrl='" + streamingUrl + '\'' +
                ", imageId=" + imageId +
                ", tags=" + tags +
                '}';
    }
}
