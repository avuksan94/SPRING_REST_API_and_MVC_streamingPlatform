package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Integer> {
}
