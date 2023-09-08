package hr.algebra.bll.service;

import hr.algebra.dal.dao.NotificationRepository;
import hr.algebra.dal.entity.Notification;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository _notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        _notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> findAll() {
        return _notificationRepository.findAll();
    }

    @Override
    public Notification findById(int id) {
        Optional<Notification> notificationOptional;
        notificationOptional = _notificationRepository.findById(id);

        if (notificationOptional.isEmpty()){
            throw new CustomNotFoundException(("Notification id not found - " + id));
        }
        return notificationOptional.get();
    }

    @Override
    public Notification save(Notification notification) {
        return _notificationRepository.save(notification);
    }

    @Override
    public void deleteById(int id) {
        _notificationRepository.deleteById(id);
    }
}
