package hr.algebra.bll.service;

import hr.algebra.dal.dao.TagRepository;
import hr.algebra.dal.entity.Tag;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository _tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        _tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return _tagRepository.findAll();
    }

    @Override
    public Tag findById(int id) {
        Optional<Tag> tagOptional;
        tagOptional = _tagRepository.findById(id);

        if (tagOptional.isEmpty()){
            throw new CustomNotFoundException(("Tag id not found - " + id));
        }

        return tagOptional.get();
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        return _tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        _tagRepository.deleteById(id);
    }

    @Override
    public Tag findByName(String tagName) {
        List<Tag> allTags = _tagRepository.findAll();

        Optional<Tag> result = allTags.stream()
                .filter(tagRes -> tagName.equals(tagRes.getName()))
                .findFirst();

        return result.get();
    }

    @Override
    public Tag findOrCreate(String tagName) {
        // Look for the tag in the database
        List<Tag> allTags = _tagRepository.findAll();

        Optional<Tag> existingTag = allTags.stream()
                .filter(tagRes -> tagName.equals(tagRes.getName()))
                .findFirst();

        // If the tag exists, return it
        if (existingTag.isPresent()) {
            return existingTag.get();
        }

        // Otherwise, create a new Tag
        Tag newTag = new Tag();
        newTag.setName(tagName);

        // Save the new tag to the database
        newTag = _tagRepository.save(newTag);

        return newTag;
    }

    @Override
    public List<Tag> getByKeyword(String keyword){
        return _tagRepository.findByKeyword(keyword);
    }

    /*
    In Java, Optional<T> is a container that may or may not contain a non-null value of type T.
    By using Optional, you can better deal with cases that might result in null values without
    having to explicitly check for null yourself. It's a way of saying that a method might
    legally return a null value and forcing the caller to deal with that possibility.
    */
}
