package com.example.blogapi.controller;

import com.example.blogapi.model.Blog;
import com.example.blogapi.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAll() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Blog> create(@Valid @RequestBody Blog blog,
                                       UriComponentsBuilder uriBuilder) {
        Blog created = blogService.create(blog);

        URI location = uriBuilder
                .path("/api/blogs/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> update(@PathVariable Long id,
                                       @Valid @RequestBody Blog blog) {
        return ResponseEntity.ok(blogService.update(id, blog));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }
}