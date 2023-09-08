package hr.algebra.dal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
@Table(name="notification")
public class Notification {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="createdAt")
    private LocalDateTime createdAt;
    @Column(name="receiverEmail")
    @NotEmpty(message = "Receiver email is required")
    private String receiverEmail;
    @Column(name="subject")
    @NotEmpty(message = "Subject email is required")
    private String subject;
    @Column(name="body")
    @NotEmpty(message = "Body email is required")
    private String body;
    @Column(name="sentAt")
    private LocalDateTime sentAt;

    public Notification() {
    }

    public Notification(LocalDateTime createdAt, String receiverEmail, String subject, String body, LocalDateTime sentAt) {
        this.createdAt = LocalDateTime.now();
        this.receiverEmail = receiverEmail;
        this.subject = subject;
        this.body = body;
        this.sentAt = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

}
