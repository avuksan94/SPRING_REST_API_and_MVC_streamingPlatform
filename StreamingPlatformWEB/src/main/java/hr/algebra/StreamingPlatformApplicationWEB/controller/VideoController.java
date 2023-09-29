package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.blModels.TagModel;
import hr.algebra.bll.blModels.VideoModel;
import hr.algebra.bll.service.GenreServiceImpl;
import hr.algebra.bll.service.ImageServiceImpl;
import hr.algebra.bll.service.TagServiceImpl;
import hr.algebra.bll.service.VideoServiceImpl;
import hr.algebra.dal.entity.Genre;
import hr.algebra.dal.entity.Image;
import hr.algebra.dal.entity.Tag;
import hr.algebra.dal.entity.Video;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html

@Controller
@RequestMapping("videos")
public class VideoController {
    //$ reference to model
    //@ link to html/form/view
    //*{} setter for entered data

    private final VideoServiceImpl _videoService;
    private final ImageServiceImpl _imageService;
    private final GenreServiceImpl _genreService;
    private final TagServiceImpl _tagService;

    public VideoController(VideoServiceImpl videoService, ImageServiceImpl imageService, GenreServiceImpl genreService, TagServiceImpl tagService) {
        _videoService = videoService;
        _imageService = imageService;
        _genreService = genreService;
        _tagService = tagService;
    }

    // add mapping for "/list"
    @GetMapping("/list")
    public String listVideos(Model theModel) {

        List<Video> videos = _videoService.findAll();
        List<Image> images = _imageService.findAll();

        Map<Integer, Image> imageMap = new HashMap<>();

        for (Video video : videos) {
            Image image = _imageService.findById(video.getImageId());
            imageMap.put(video.getImageId(), image);
        }

        // add to the spring model
        theModel.addAttribute("videos", videos);
        theModel.addAttribute("images",imageMap);

        return "videos/list-videos";
    }

    // add mapping for "/list"
    @GetMapping("/listAdmin")
    public String listVideosAdmin(Model theModel) {

        List<Video> videos = _videoService.findAll();
        List<Image> images = _imageService.findAll();

        Map<Integer, Image> imageMap = new HashMap<>();

        for (Video video : videos) {
            Image image = _imageService.findById(video.getImageId());
            imageMap.put(video.getImageId(), image);
        }

        // add to the spring model
        theModel.addAttribute("videos", videos);
        theModel.addAttribute("images",imageMap);

        return "videos/list-videos-admin";
    }

    @GetMapping("/showFormForAddVideo")
    public String showFormForAddVideo(Model theModel){

        //create the model attribute to bind form data
        VideoModel video = new VideoModel();
        theModel.addAttribute("video", video);

        List<Genre> genres = _genreService.findAll();
        theModel.addAttribute("genres", genres);

        List<Image> images = _imageService.findAll();
        theModel.addAttribute("images", images);

        return "videos/video-form";
    }

    @GetMapping("/showSelectedVideo")
    public String showSelected(@RequestParam("videoId") int theId, Model theModel){
        //get the video from the service
        Video video = _videoService.findById(theId);

        //Genres
        List<Genre>  genres = _genreService.findAll();

        Genre genre = genres.stream()
                .filter(g -> g.getId() == video.getGenreId())
                .findFirst()
                .orElse(null);
        theModel.addAttribute("genre", genre);

        //All images
        List<Image> images = _imageService.findAll();

        Image image = images.stream()
                .filter(i -> i.getId() == video.getImageId())
                .findFirst()
                .orElse(null);
        theModel.addAttribute("image", image);

        Set<Tag> tagsForVideo = _videoService.getTagsForVideo(video.getId());
        theModel.addAttribute("tags", tagsForVideo);

        //set video int the model to prepopulate the form
        theModel.addAttribute("video", video);

        //send over to our form
        return "videos/video-display-info";
    }

    @PostMapping("/save")
    public String saveVideo(@Valid @ModelAttribute("video") VideoModel video, BindingResult bindingResult, @RequestParam String tags, Model model) {

        List<Genre> genres = _genreService.findAll();
        model.addAttribute("genres", genres);

        List<Image> images = _imageService.findAll();
        model.addAttribute("images", images);

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "videos/video-form";
        }

        List<Video> allDBVideos = _videoService.findAll();

        Optional<Video> result = allDBVideos.stream()
                .filter(videoRes -> video.getName().equals(videoRes.getName()))
                .findFirst();

        if(result.isPresent() && result.get().getId() != video.getId()){
            model.addAttribute("errorMessage", "Video with that name already exists!" + video.getId());
            return "videos/video-form";
        }

        // Check if its an update or a new video
        Video videoToAdd;

        if(video.getId() > 0) {
            videoToAdd = _videoService.findById(video.getId());
        } else {
            videoToAdd = new Video();
            videoToAdd.setCreatedAt(LocalDateTime.now());
        }

        // Set properties of videoToAdd from the forms videoModel
        videoToAdd.setName(video.getName());
        videoToAdd.setDescription(video.getDescription());
        videoToAdd.setGenreId(video.getGenreId());
        videoToAdd.setImageId(video.getImageId());
        videoToAdd.setTotalSeconds(video.getTotalSeconds());
        videoToAdd.setStreamingUrl(video.getStreamingUrl());

        String[] split = tags.split(",");
        Set<Tag> tagsToAdd = new HashSet<>();
        for (String str : split) {
            if (str != null && !str.trim().isEmpty()) {
                Tag tag = new Tag();
                tag.setName(str.trim());
                tagsToAdd.add(tag);
            }
        }
        Set<Tag> existingTags = tagsToAdd.stream()
                .map(tag -> _tagService.findOrCreate(tag.getName()))
                .collect(Collectors.toSet());
        videoToAdd.setTags(existingTags);

        _videoService.save(videoToAdd);

        return "redirect:/videos/list";
    }

    @GetMapping("/showFormForUpdateVideo")
    public String showFormForUpdateVideo(@RequestParam("videoId") int theId,Model theModel){
        //get the video from the service
        Video video = _videoService.findById(theId);

        List<Genre> genres = _genreService.findAll();
        theModel.addAttribute("genres", genres);

        List<Image> images = _imageService.findAll();
        theModel.addAttribute("images", images);

        VideoModel videoModel = new VideoModel();

        videoModel.setId(video.getId());
        videoModel.setName(video.getName());
        videoModel.setDescription(video.getDescription());
        videoModel.setCreatedAt(video.getCreatedAt());
        videoModel.setGenreId(video.getGenreId());
        videoModel.setImageId(video.getImageId());
        videoModel.setTotalSeconds(video.getTotalSeconds());
        videoModel.setStreamingUrl(video.getStreamingUrl());

        Set<TagModel> tagsForVideoModel = new HashSet<>();

        for (Tag tag : video.getTags()){
            TagModel tagModel = new TagModel();
            tagModel.setName(tag.getName());
            tagsForVideoModel.add(tagModel);
        }

        videoModel.setTags(tagsForVideoModel);

        String tagsAsString = video.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));

        theModel.addAttribute("tagsAsString", tagsAsString);

        theModel.addAttribute("video", videoModel);

        theModel.addAttribute("selectedImageId", videoModel.getImageId());

        //send over to our form
        return "videos/video-form-update";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("videoId") int theId){
        //delete the tag
        _videoService.deleteById(theId);

        //redirect to the /tags/list
        return "redirect:/videos/list";
    }

    public boolean tagAlreadyExists(String tagName){
        List<Tag> allTags = _tagService.findAll();

        Optional<Tag> result = allTags.stream()
                .filter(tagRes -> tagName.equals(tagRes.getName()))
                .findFirst();

        return result.isPresent();
    }
}
