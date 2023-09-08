package hr.algebra.bll.serviceTests;

import hr.algebra.bll.service.NotificationServiceImpl;
import hr.algebra.dal.dao.NotificationRepository;
import hr.algebra.dal.entity.Notification;
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
public class NotificationServiceTest {
    @Mock
    private NotificationRepository _notificationRepository;
    @InjectMocks
    private NotificationServiceImpl _notificationService;

    @Test
    public void testFindAll() {
        when(_notificationRepository.findAll()).thenReturn(Arrays.asList(new Notification(), new Notification()));
        List<Notification> notifications = _notificationService.findAll();

        assertEquals(2, notifications.size());
    }

    @Test
    public void testFindByIdFound() {
        int id = 1;
        when(_notificationRepository.findById(id)).thenReturn(Optional.of(new Notification()));
        Notification notification = _notificationService.findById(id);

        assertNotNull(notification);
    }

    @Test
    public void testSave() {
        Notification notification = new Notification();
        when(_notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Notification savedNotification = _notificationService.save(new Notification());

        assertNotNull(savedNotification);
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        _notificationService.deleteById(id);

        verify(_notificationRepository, times(1)).deleteById(id);
    }
}
