package hr.algebra.streamingPlatform.rest;

import hr.algebra.bll.blModels.NotificationModel;
import hr.algebra.bll.service.NotificationService;
import hr.algebra.dal.entity.Notification;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private final NotificationService _notificationService;

    public NotificationController(NotificationService notificationService) {
        _notificationService = notificationService;
    }

    @Async
    @GetMapping("/notifications")
    public CompletableFuture<List<Notification>> findAll(){
        return CompletableFuture.completedFuture(_notificationService.findAll());
    }

    @Async
    @GetMapping("/notifications/{notificationId}")
    public CompletableFuture<Notification> getNotification(@PathVariable int notificationId){
        Notification notification = _notificationService.findById(notificationId);

        return CompletableFuture.completedFuture(notification);
    }

    @Async
    @PostMapping("/notifications")
    public CompletableFuture<NotificationModel> createNotification(@Valid @RequestBody NotificationModel notificationModel){
        Notification notification = new Notification(
                LocalDateTime.now(),
                notificationModel.getReceiverEmail(),
                notificationModel.getSubject(),
                notificationModel.getBody(),
                null
        );

        notification.setId(0);
        _notificationService.save(notification);
        return CompletableFuture.completedFuture(notificationModel);
    }

    @Async
    @PutMapping("/notifications/{notificationId}")
    public CompletableFuture<NotificationModel> updateNotification(@PathVariable int notificationId, @Valid @RequestBody NotificationModel notificationModel){
        Notification existingNotification = _notificationService.findById(notificationId);

        if (existingNotification == null) {
            throw new CustomNotFoundException("Notification not found for id: " + notificationId);
        }

        existingNotification.setReceiverEmail(notificationModel.getReceiverEmail());
        existingNotification.setSubject(notificationModel.getSubject());
        existingNotification.setBody(notificationModel.getBody());

        _notificationService.save(existingNotification);

        return CompletableFuture.completedFuture(notificationModel);
    }

    @Async
    @DeleteMapping("/notifications/{notificationId}")
    public CompletableFuture<String> deleteNotification(@PathVariable int notificationId){
        Notification notificationToDelete = _notificationService.findById(notificationId);
        if (notificationToDelete == null){
            throw new CustomNotFoundException(("Notification id not found - " + notificationId));
        }
        _notificationService.deleteById(notificationId);

        return CompletableFuture.completedFuture("Deleted notification with Id: " + notificationId);
    }

}
