package io.github.f4lt3ring.jagatudkalendrirakendus.DTO;

public class ActionResponse {
    private String status;
    private String message;
    private String uid;

    // getters & setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}