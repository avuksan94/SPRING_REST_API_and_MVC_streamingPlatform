package hr.algebra.bll.service;

import hr.algebra.dal.dao.GenreRepository;
import hr.algebra.dal.dao.ImageRepository;
import hr.algebra.dal.dao.VideoRepository;
import hr.algebra.dal.entity.Genre;
import hr.algebra.dal.entity.Image;
import hr.algebra.dal.entity.Tag;
import hr.algebra.dal.entity.Video;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository _videoRepository;
    private final GenreRepository _genreRepository;
    private final ImageRepository _imageRepository;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, GenreRepository genreRepository, ImageRepository imageRepository) {
        _videoRepository = videoRepository;
        _genreRepository = genreRepository;
        _imageRepository = imageRepository;
    }

    @Override
    public List<Video> findAll() {
        return _videoRepository.findAll();
    }

    @Override
    public Video findById(int id) {
        Optional<Video> videoOptional;
        videoOptional = _videoRepository.findById(id);

        if (videoOptional.isEmpty()){
            throw new CustomNotFoundException(("Video id not found - " + id));
        }

        return videoOptional.get();
    }

    @Override
    public Video save(Video video) {
        Optional<Genre> genreOptional;
        genreOptional = _genreRepository.findById(video.getGenreId());

        Optional<Image> imageOptional;
        imageOptional = _imageRepository.findById(video.getImageId());

        if (genreOptional.isEmpty()){
            throw new CustomNotFoundException(("Genre id not found - " + video.getGenreId()));
        }

        if (imageOptional.isEmpty()){
            throw new CustomNotFoundException(("Image id not found - " + video.getImageId()));
        }
        return _videoRepository.save(video);
    }

    @Override
    public Set<Tag> getTagsForVideo(int id) {
        Optional<Video> videoOpt = _videoRepository.findById(id);
        if (videoOpt.isPresent()) {
            return videoOpt.get().getTags();
        }
        return Collections.emptySet();
    }

    @Override
    public void deleteById(int id) {
        _videoRepository.deleteById(id);
    }
}
