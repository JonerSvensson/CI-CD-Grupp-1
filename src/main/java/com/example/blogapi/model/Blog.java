package com.example.blogapi.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class Blog {

    private Long id;
    @NotBlank(message = "Titeln får inte vara tom")
    private String title;

    @NotBlank(message = "Innehållet får inte vara tomt")
    private String body;

    @NotBlank(message = "Författaren får inte vara tom")
    private String author;
    private LocalDateTime createdAt;

    public Blog() {
    }

    public Blog(String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }


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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}