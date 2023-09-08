package hr.algebra.streamingPlatform.rest;

import hr.algebra.dal.entity.Video;
import hr.algebra.bll.blModels.VideoModel;
import hr.algebra.dal.entity.Tag;
import hr.algebra.bll.service.TagService;
import hr.algebra.bll.service.VideoServiceImpl;
import hr.algebra.utils.alreadyExistsErrors.CustomAlreadyExistsException;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VideoController {
    @Autowired
    private final VideoServiceImpl _videoService;
    @Autowired
    private final TagService _tagService;

    public VideoController(VideoServiceImpl videoService, TagService tagService) {
        _videoService = videoService;
        _tagService = tagService;
    }

    @Async
    @GetMapping("/videos")
    public CompletableFuture<List<Video>> findAll(){
        return CompletableFuture.completedFuture(_videoService.findAll());
    }

    @Async
    @GetMapping("/videos/{videoId}")
    public CompletableFuture<Video> getVideo(@PathVariable int videoId){
        Video video = _videoService.findById(videoId);

        return CompletableFuture.completedFuture(video);
    }

    @Async
    @PostMapping("/videos")
    public CompletableFuture<VideoModel> addVideo(@Valid @RequestBody VideoModel videoModel){

        List<Video> allDBVideos = _videoService.findAll();

        //this is firstOrDefault in c#
        Optional<Video> result = allDBVideos.stream()
                .filter(videoRes -> videoModel.getName().equals(videoRes.getName()))
                .findFirst();

        if(result.isPresent()){
            throw new CustomAlreadyExistsException("Video with that name already exists: " + ' ' + videoModel.getName());
        }

        Set<Tag> existingTags = videoModel.getTags().stream()
                .map(tag -> _tagService.findOrCreate(tag.getName()))
                .collect(Collectors.toSet());

        Video video = new Video();
        // Set the tags to the existing ones
        video.setTags(existingTags);

        video.setCreatedAt(videoModel.getCreatedAt());
        video.setName(videoModel.getName());
        video.setDescription(videoModel.getDescription());
        video.setGenreId(videoModel.getGenreId());
        video.setTotalSeconds(videoModel.getTotalSeconds());
        video.setStreamingUrl(videoModel.getStreamingUrl());
        video.setImageId(videoModel.getImageId());

        video.setId(0);

        _videoService.save(video);

        return CompletableFuture.completedFuture(videoModel);
    }

    @Async
    @PutMapping("/videos/{id}")
    public CompletableFuture<VideoModel> updateVideo(@PathVariable int id, @Valid @RequestBody VideoModel videoModel) {

        Video existingVideo = _videoService.findById(id);

        if (existingVideo == null) {
            throw new CustomNotFoundException("Video not found for id: " + id);
        }

        existingVideo.setCreatedAt(videoModel.getCreatedAt());
        existingVideo.setName(videoModel.getName());
        existingVideo.setDescription(videoModel.getDescription());
        existingVideo.setGenreId(videoModel.getGenreId());
        existingVideo.setTotalSeconds(videoModel.getTotalSeconds());
        existingVideo.setStreamingUrl(videoModel.getStreamingUrl());
        existingVideo.setImageId(videoModel.getImageId());

        Set<Tag> existingTags = videoModel.getTags().stream()
                .map(tag -> _tagService.findOrCreate(tag.getName()))
                .collect(Collectors.toSet());

        existingVideo.setTags(existingTags);

        _videoService.save(existingVideo);

        return CompletableFuture.completedFuture(videoModel);
    }

    @Async
    @DeleteMapping("/videos/{videoId}")
    public CompletableFuture<String> deleteVideo(@PathVariable int videoId){
        Video videoToDelete = _videoService.findById(videoId);
        if (videoToDelete == null){
            throw new CustomNotFoundException(("Video id not found - " + videoId));
        }
        _videoService.deleteById(videoId);

        return CompletableFuture.completedFuture("Deleted video with ID: " + videoId);
    }

    public boolean tagAlreadyExists(String tagName){
        List<Tag> allTags = _tagService.findAll();

        Optional<Tag> result = allTags.stream()
                .filter(tagRes -> tagName.equals(tagRes.getName()))
                .findFirst();

        if(result.isPresent()){
            return true;
        }

        return false;
    }

}
