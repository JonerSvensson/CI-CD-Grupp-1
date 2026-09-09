package com.example.blogapi.repository;

import com.example.blogapi.model.Blog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BlogRepository {

    private final List<Blog> posts = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public BlogRepository() {
        Blog post1 = new Blog("Första inlägget", "Det här är innehållet i första inlägget.", "Anna");
        post1.setId(nextId.getAndIncrement());
        posts.add(post1);

        Blog post2 = new Blog("Andra inlägget", "Här kommer lite mer text att läsa.", "Erik");
        post2.setId(nextId.getAndIncrement());
        posts.add(post2);
    }

    public List<Blog> findAll() {
        return posts;
    }

    public Optional<Blog> findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    public Blog save(Blog blog) {
        blog.setId(nextId.getAndIncrement());
        posts.add(blog);
        return blog;
    }

    public Optional<Blog> update(Long id, Blog updatedBlog) {
        Optional<Blog> existing = findById(id);
        existing.ifPresent(blog -> {
            blog.setTitle(updatedBlog.getTitle());
            blog.setBody(updatedBlog.getBody());
            blog.setAuthor(updatedBlog.getAuthor());
        });
        return existing;
    }

    public boolean deleteById(Long id) {
        return posts.removeIf(post -> post.getId().equals(id));
    }
}