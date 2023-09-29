package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.service.ImageServiceImpl;
import hr.algebra.dal.entity.Image;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("images")
public class ImageController {
    private final ImageServiceImpl _imageService;

    public ImageController(ImageServiceImpl imageService) {
        _imageService = imageService;
    }

    @GetMapping("/list")
    public String listGenres(Model theModel) {

        List<Image> images = _imageService.findAll();

        // add to the spring model
        theModel.addAttribute("images", images);

        return "images/list-images";
    }

    @GetMapping("/showFormForAddImage")
    public String showFormForAddImage(Model theModel){

        //create the model attribute to bind form data
        Image image =  new Image();
        theModel.addAttribute("image", image);

        return "images/image-form";
    }

    @GetMapping("/showFormForUpdateImage")
    public String showFormForUpdateImage(@RequestParam("imageId") int theId, Model theModel){
        //get the image from the service
        Image image = _imageService.findById(theId);

        theModel.addAttribute("image", image);

        //send over to our form
        return "images/image-form";
    }

    @PostMapping("/save")
    public String saveImage(@Valid @ModelAttribute("image") Image image, BindingResult bindingResult, Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "images/image-form";
        }

        List<Image> allDBImages = _imageService.findAll();

        Optional<Image> result = allDBImages.stream()
                .filter(imageRes -> image.getContent().equals(imageRes.getContent()))
                .findFirst();

        if(result.isPresent()){
            model.addAttribute("errorMessage", "Image already added to database!");
            return "images/image-form";
        }

        _imageService.save(image);

        return "redirect:/images/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("imageId") int theId){
        //delete the image
        _imageService.deleteById(theId);

        //redirect to the /genres/list
        return "redirect:/images/list";
    }
}
