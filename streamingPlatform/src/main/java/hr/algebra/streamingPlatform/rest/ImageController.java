package hr.algebra.streamingPlatform.rest;

import hr.algebra.dal.entity.Image;
import hr.algebra.bll.service.ImageServiceImpl;
import hr.algebra.utils.alreadyExistsErrors.CustomAlreadyExistsException;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class ImageController {
    private ImageServiceImpl _imageService;

    public ImageController(ImageServiceImpl imageService) {
        _imageService = imageService;
    }

    @Async
    @GetMapping("/images")
    public CompletableFuture<List<Image>> findAll(){
        return CompletableFuture.completedFuture(_imageService.findAll());
    }

    @Async
    @GetMapping("/images/{imageId}")
    public CompletableFuture<Image> getImage(@PathVariable int imageId){
        Image image = _imageService.findById(imageId);

        return CompletableFuture.completedFuture(image);
    }


    @Async
    @PostMapping("/images")
    public CompletableFuture<Image> addImage(@Valid @RequestBody Image image){

        List<Image> allDBImages = _imageService.findAll();

        //this is firstOrDefault in c#
        Optional<Image> result = allDBImages.stream()
                .filter(imageRes -> image.getContent().equals(imageRes.getContent()))
                .findFirst();

        if(result.isPresent()){
            throw new CustomAlreadyExistsException("Image with that Content already exists: " + ' ' + image.getContent());
        }

        image.setId(0);
        return CompletableFuture.completedFuture(_imageService.save(image));
    }

    @Async
    @PutMapping("/images")
    public CompletableFuture<Image> updateGenre(@Valid @RequestBody Image image){
        return CompletableFuture.completedFuture(_imageService.save(image));
    }

    @Async
    @DeleteMapping("/images/{imageId}")
    public CompletableFuture<String> deleteImage(@PathVariable int imageId){
        Image imageToDelete = _imageService.findById(imageId);
        if (imageToDelete == null){
            throw new CustomNotFoundException(("Image id not found - " + imageId));
        }
        _imageService.deleteById(imageId);

        return CompletableFuture.completedFuture("Deleted image with ID: " + imageId);
    }
}
