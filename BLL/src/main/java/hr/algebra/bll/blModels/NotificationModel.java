package hr.algebra.bll.blModels;

public class NotificationModel {
    private int id;
    private String receiverEmail;
    private String subject;
    private String body;

    public NotificationModel() {
    }

    public NotificationModel(String receiverEmail, String subject, String body) {
        this.receiverEmail = receiverEmail;
        this.subject = subject;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "NotificationModel{" +
                "id=" + id +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
