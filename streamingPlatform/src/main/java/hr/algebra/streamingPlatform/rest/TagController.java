package hr.algebra.streamingPlatform.rest;

import hr.algebra.dal.entity.Tag;
import hr.algebra.bll.service.TagServiceImpl;
import hr.algebra.utils.alreadyExistsErrors.CustomAlreadyExistsException;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class TagController {
    @Autowired
    private final TagServiceImpl _tagService;

    public TagController(TagServiceImpl tagService) {
        _tagService = tagService;
    }

    @Async
    @GetMapping("/tags")
    public CompletableFuture<List<Tag>> findAll(){
        return CompletableFuture.completedFuture(_tagService.findAll());
    }

    @Async
    @GetMapping("/tags/{tagId}")
    public CompletableFuture<Tag> getTag(@PathVariable int tagId){
        Tag tag = _tagService.findById(tagId);

        return CompletableFuture.completedFuture(tag);
    }

    @Async
    @PostMapping("/tags")
    public CompletableFuture<Tag> addTag(@Valid @RequestBody Tag tag){

        List<Tag> allDBTags = _tagService.findAll();

        //this is firstOrDefault in c#
        Optional<Tag> result = allDBTags.stream()
                .filter(tagRes -> tag.getName().equals(tagRes.getName()))
                .findFirst();

        if(result.isPresent()){
            throw new CustomAlreadyExistsException("Tag with that name already exists: " + ' ' + tag.getName());
        }

        tag.setId(0);
        return CompletableFuture.completedFuture(_tagService.save(tag));
    }

    @Async
    @PutMapping("/tags/{id}")
    public CompletableFuture<Tag> updateTag(@PathVariable int id,@Valid @RequestBody Tag tag){
        Tag existingTag = _tagService.findById(id);

        if (existingTag == null) {
            throw new CustomNotFoundException("Tag not found for id: " + id);
        }

        existingTag.setName(tag.getName());

        return CompletableFuture.completedFuture(_tagService.save(existingTag));
    }

    @Async
    @DeleteMapping("/tags/{tagId}")
    public CompletableFuture<String> deleteTag(@PathVariable int tagId){
        Tag tagToDelete = _tagService.findById(tagId);
        if (tagToDelete == null){
            throw new CustomNotFoundException(("Tag id not found - " + tagId));
        }
        _tagService.deleteById(tagId);

        return CompletableFuture.completedFuture("Deleted tag with ID: " + tagId);
    }
}
