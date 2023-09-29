package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.service.TagServiceImpl;
import hr.algebra.dal.entity.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("tags")
public class TagController {
    private TagServiceImpl _tagService;

    public TagController(TagServiceImpl tagService) {
        _tagService = tagService;
    }

    // add mapping for "/list"
    @GetMapping("/list")
    public String listVideos(Model theModel) {

        List<Tag> tags = _tagService.findAll();

        // add to the spring model
        theModel.addAttribute("tags", tags);

        return "tags/list-tags";
    }

    @GetMapping("/listSearch")
    public String searchTags(Tag Tag, Model model, String keyword) {
        List<Tag> tags;
        if(keyword != null) {
            tags = _tagService.getByKeyword(keyword);
        }else {
            tags = _tagService.findAll();
        }
        model.addAttribute("tags", tags);

        return "tags/list-tags";
    }

    @GetMapping("/showFormForAddTag")
    public String showFormForAddTag(Model theModel){

        //create the model attribute to bind form data
        Tag tag =  new Tag();
        theModel.addAttribute("tag", tag);

        return "tags/tag-form";
    }

    @GetMapping("/showFormForUpdateTag")
    public String showFormForUpdateTag(@RequestParam("tagId") int theId,Model theModel){
        //get the tag from the service
        Tag tag = _tagService.findById(theId);

        //set employee int the model to prepopulate the form
        theModel.addAttribute("tag", tag);

        //send over to our form
        return "tags/tag-form";
    }

    @PostMapping("/save")
    public String saveTag(@Valid @ModelAttribute("tag") Tag tag, BindingResult bindingResult, Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "tags/tag-form";
        }

        List<Tag> allDBTags = _tagService.findAll();

        Optional<Tag> result = allDBTags.stream()
                .filter(tagRes -> tag.getName().equals(tagRes.getName()))
                .findFirst();

        if(result.isPresent()){
            model.addAttribute("errorMessage", "Tag with that name already exists!");
            return "tags/tag-form";
        }

        _tagService.save(tag);

        return "redirect:/tags/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("tagId") int theId){
        //delete the tag
        _tagService.deleteById(theId);

        //redirect to the /tags/list
        return "redirect:/tags/list";
    }
}
