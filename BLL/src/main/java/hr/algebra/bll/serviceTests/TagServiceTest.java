package hr.algebra.bll.serviceTests;

import hr.algebra.bll.service.TagServiceImpl;
import hr.algebra.dal.dao.TagRepository;
import hr.algebra.dal.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository _tagRepository;
    @InjectMocks
    private TagServiceImpl _tagService;

    @Test
    public void testFindAll() {
        when(_tagRepository.findAll()).thenReturn(Arrays.asList(new Tag(), new Tag()));
        List<Tag> tags = _tagService.findAll();

        assertEquals(2, tags.size());
    }

    @Test
    public void testFindByIdFound() {
        int id = 1;
        when(_tagRepository.findById(id)).thenReturn(Optional.of(new Tag()));
        Tag tag = _tagService.findById(id);

        assertNotNull(tag);
    }

    @Test
    public void testSave() {
        Tag tag = new Tag("derp");
        when(_tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag savedTag = _tagService.save(new Tag());

        assertNotNull(savedTag);
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        _tagService.deleteById(id);

        verify(_tagRepository, times(1)).deleteById(id);
    }
}
