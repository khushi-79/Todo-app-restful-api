package com.restfulApiTodo.restful.api.to_do.app;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;

@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public TodoItem(Long id, String title, String description, boolean completed, Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Parameterized constructor for title, description, and status
    public TodoItem(String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = new Date(); // Automatically set creation date
        this.updatedAt = new Date(); // Automatically set updated date
    }

    public TodoItem() {
    }

//    @PrePersist
//    protected void onCreate(){
//        createdAt = new Date();
//    }
//
//    @PrePersist
//    protected void onUpdate(){
//        updatedAt = new Date();
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
