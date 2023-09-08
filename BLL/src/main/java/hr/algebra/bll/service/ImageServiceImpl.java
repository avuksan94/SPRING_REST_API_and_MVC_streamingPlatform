package hr.algebra.bll.service;

import hr.algebra.dal.dao.ImageRepository;
import hr.algebra.dal.entity.Image;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    private ImageRepository _imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        _imageRepository = imageRepository;
    }

    @Override
    public List<Image> findAll() {
        return _imageRepository.findAll();
    }

    @Override
    public Image findById(int id) {
        Optional<Image> imageOptional = _imageRepository.findById(id);

        if (imageOptional.isEmpty()){
            throw new CustomNotFoundException("Image id not found - " + id);
        }

        return imageOptional.get();
    }

    @Override
    @Transactional
    public Image save(Image image) {
        return _imageRepository.save(image);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        _imageRepository.deleteById(id);
    }
}
