package hr.algebra.bll.service;

import hr.algebra.dal.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();
    Notification findById(int id);
    Notification save(Notification notification);
    void deleteById(int id);
}
